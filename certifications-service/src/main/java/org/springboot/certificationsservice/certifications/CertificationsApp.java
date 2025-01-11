package org.springboot.certificationsservice.certifications;


import jakarta.persistence.*;
import lombok.*;
import org.springboot.certificationsservice.category.CategoryApp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "certifications")
public class CertificationsApp {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private double avaiblity;
    private double price;
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryApp category;

}
