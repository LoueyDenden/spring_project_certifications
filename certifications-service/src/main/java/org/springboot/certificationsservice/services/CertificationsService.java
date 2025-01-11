package org.springboot.certificationsservice.services;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springboot.certificationsservice.category.CategoryApp;
import org.springboot.certificationsservice.exception.CertificationsPurchaseException;
import org.springboot.certificationsservice.certifications.*;
import org.springboot.certificationsservice.mapper.CertificationMapper;
import org.springboot.certificationsservice.repository.CertificationRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CertificationsService {
    private final CertificationRepo repository;
    private final CertificationMapper mapper;


    //find all by pagination
    public Page<CertificationsApp> getCertificationsPagination(
            String name,
            Pageable pageable
    ){
        return repository.findByNameContaining(name,pageable);
    }

    //IMAGEEEE
    private String giveMeNewName(String oldName)
    {

        String firstpart = oldName.substring(0, oldName.lastIndexOf("."));
        String secondpart = oldName.substring(oldName.lastIndexOf(".") + 1);
        return firstpart + System.currentTimeMillis() + "." + secondpart;
    }

    @Value("${uploads.dir}")
    private String uploadDir;
    public String saveImage2(MultipartFile mf) throws IOException {
        String newName = giveMeNewName(mf.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath))
            Files.createDirectory(uploadPath);
        Path pathFile = uploadPath.resolve(newName);
        Files.write(pathFile, mf.getBytes());
        return newName;
    }

    //creates a certification and convert from certification request to certificationapp then save it to BDD
    public Integer createCertification(
            CertificationsRequest request,
            MultipartFile mf
    ) throws IOException {
        var certification = mapper.toCertification(request);
        if (!mf.isEmpty()){
            certification.setImage(saveImage2(mf));
        }
        return repository.save(certification).getId();
    }

    //update Certification
    public Integer updateCertification(
            CertificationsRequest request,
            Integer certificationId
    ){
        var certificationToUpdate = repository.findById(certificationId).orElseThrow(EntityNotFoundException::new);
        mergeCertification(certificationToUpdate,request);
        repository.save(certificationToUpdate);
        return certificationToUpdate.getId();
    }

    private void mergeCertification(CertificationsApp certification, CertificationsRequest certificationsRequest) {
        CategoryApp category = new CategoryApp();
        category.setId(certificationsRequest.categoryId());
        if (StringUtils.isNotBlank(certificationsRequest.name())){
            certification.setName(certificationsRequest.name());
        }
        if (StringUtils.isNotBlank(certificationsRequest.description())){
            certification.setDescription(certificationsRequest.description());
        }
        if (certificationsRequest.price()!=0.0){
            certification.setPrice(certificationsRequest.price());
        }
        if (certificationsRequest.avaiblity()!=0.0){
            certification.setAvaiblity(certificationsRequest.avaiblity());
        }
        if (certificationsRequest.categoryId()!=null){
            certification.setCategory(category);
        }
    }

    //delete Certification
    public Integer deleteCertification(Integer certificationId) {
        repository.deleteById(certificationId);
        return certificationId;
    }

    //return a certification by id and convert from certificationapp to certificationResponse if there is no certification with that it, it throw excp
    public CertificationsApp findById(Integer id) {
        return repository.findById(id)

                .orElseThrow(() -> new EntityNotFoundException("Certification not found with ID:: " + id));
    }

    //return all certifications and convert from certificationApp to certification response
    public List<CertificationsApp> findAll() {
        return repository.findAll();
    }


    @Transactional(rollbackFor = CertificationsPurchaseException.class)
    public List<CertificationPurchaseResponse> purchaseCertifications(
            List<CertificationPurchaseRequest> request
    ) {
        //get certificationsIds that will be purchased (certificationIds is a list)
        var certificationIds = request
                .stream()
                .map(CertificationPurchaseRequest::certificationId)
                .toList();
        //get Certifications from BDD with redifined method in certificationrepo
        var storedCertifications = repository.findAllByIdInOrderById(certificationIds);
        // if certificationIds != storedCertifications (fetched from BDD) that means there is a certification or more that not exist in BDD
        if (certificationIds.size() != storedCertifications.size()) {
            throw new CertificationsPurchaseException("One or more certifications does not exist");
        }

        //sorting certificationids from request
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(CertificationPurchaseRequest::certificationId))
                .toList();

        var purchasedCertifications = new ArrayList<CertificationPurchaseResponse>();
        for (int i = 0; i < storedCertifications.size(); i++) {
            var certification = storedCertifications.get(i);
            var certificationRequest = sortedRequest.get(i);
            if (certification.getAvaiblity() < certificationRequest.quantity()) {
                throw new CertificationsPurchaseException("Insufficient stock quantity for certification with ID:: " + certificationRequest.certificationId());
            }
            var newAvailableQuantity = certification.getAvaiblity() - certificationRequest.quantity();
            certification.setAvaiblity(newAvailableQuantity);
            repository.save(certification);
            purchasedCertifications.add(mapper.toCertificationPurchaseResponse(certification, certificationRequest.quantity()));
        }
        return purchasedCertifications;
    }


}
