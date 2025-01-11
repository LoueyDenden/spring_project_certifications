package org.springboot.orderservice.services;



import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springboot.orderservice.certifications.CertificationClient;
import org.springboot.orderservice.exception.BusinessException;
import org.springboot.orderservice.certifications.PurchaseRequest;
import org.springboot.orderservice.certifications.PurchaseResponse;
import org.springboot.orderservice.library.LibraryClient;
import org.springboot.orderservice.library.PurchaseLibrary;
import org.springboot.orderservice.mapper.OrderMapper;
import org.springboot.orderservice.order.OrderApp;
import org.springboot.orderservice.order.OrderRequest;
import org.springboot.orderservice.orderLine.OrderLineRequestWithoutId;
import org.springboot.orderservice.repository.OrderRepository;
import org.springboot.orderservice.user.UserClient;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final UserClient userClient;
    private final CertificationClient certificationClient;
    private final OrderLineService orderLineService;
    private final LibraryClient libraryClient;


    double amount=0;
    @Transactional
    public Integer createOrder(OrderRequest request, String token) {
        var username = request.username();
        var user = this.userClient.findUserByUsername(username,token)
                .orElseThrow(() -> new BusinessException("Cannot create order:: No user exists with the provided username"));

        var purchasedCertifications = certificationClient.purchaseCertifications(request.certifications(),token);

        for (PurchaseResponse purchaseResponse : purchasedCertifications) {
             amount = amount + purchaseResponse.price();
        }

        var order = this.repository.save(mapper.toOrder(request,amount));

        for (PurchaseRequest purchaseRequest : request.certifications()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequestWithoutId(
                            order.getId(),
                            purchaseRequest.certificationId(),
                            purchaseRequest.quantity()
                    )
            );
        }


        PurchaseLibrary purchaseLibrary = new PurchaseLibrary(purchasedCertifications,username);

        libraryClient.purchaseLibrary(purchaseLibrary,token);

        return order.getId();
    }

    public List<OrderApp> findAllOrders() {
        return this.repository.findAll();
    }

    public List<OrderApp> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public OrderApp findById(Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }

}
