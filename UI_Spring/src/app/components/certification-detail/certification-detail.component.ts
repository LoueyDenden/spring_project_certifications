import { Component } from '@angular/core';
import {CertificationsService} from "../../services/certifications.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-certification-detail',
  templateUrl: './certification-detail.component.html',
  styleUrl: './certification-detail.component.css'
})
export class CertificationDetailComponent {
  certification: any;
  id: any;
  certificationAdded: any;

  image: string | ArrayBuffer | null = '';
  constructor(private _certification: CertificationsService, private act: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = this.act.snapshot.paramMap.get('id');
    this._certification.getCertificationById(this.id)
      .subscribe(res => {
        this.certification = res;
        this.loadImage();
      })
  }
  addToCart(certification: any) {
    this._certification.addToCart(certification);
    this.certificationAdded=`${certification.name} added to cart.`;
  }

  loadImage(): void {
    this._certification.GetCertificationsImages(this.certification.id).subscribe(
      (imageBlob: Blob) => {
        const reader = new FileReader();
        reader.onloadend = () => {
          this.image = reader.result; // Sets the image URL for binding
        };
        reader.readAsDataURL(imageBlob);
      },
      (error) => {
        console.error('Error loading image', error);
      }
    );
  }
}
