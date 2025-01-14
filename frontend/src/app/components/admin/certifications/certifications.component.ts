import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CertificationsService} from "../../../services/certifications.service";

@Component({
  selector: 'app-certifications',
  templateUrl: './certifications.component.html',
  styleUrl: './certifications.component.css'
})
export class CertificationsComponent {
  constructor(private _certifications:CertificationsService,private router:Router) {
  }
  certifications:any;
  error:any;
  ngOnInit() {
    // this.username=this.act.snapshot.paramMap.get('username');
    this._certifications.getAllCertifications().subscribe(res=>{
      this.certifications=res;
    },err=>{
      console.log(err);
    })
  }
  deleteGame(id:any){
    this._certifications.deleteCertification(id).subscribe(res=>{
      console.log(res)
      location.reload();
    },error => {
      console.log(error)
    })
    this.router.navigate(['/home']);
  }
}
