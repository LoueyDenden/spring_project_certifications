import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CertificationsService} from "../../../services/certifications.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit
{
  id:any;
  categories:any;

  constructor(private _certifications:CertificationsService,private router:Router) {
  }
    ngOnInit() {
      this._certifications.getCategory().subscribe(res=>{
        this.categories=res;
        console.log(res)
      },err=>{
        console.log(err);
      })
    }

  deleteCategory(id:any){
    this._certifications.deleteCat(id).subscribe(res=>{
      console.log(res)
      location.reload();
    },error => {
      console.log(error)
    })
  }



}
