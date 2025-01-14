import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CertificationsService} from "../../../../services/certifications.service";

@Component({
  selector: 'app-createcategory',
  templateUrl: './createcategory.component.html',
  styleUrl: './createcategory.component.css'
})
export class CreatecategoryComponent implements OnInit{
  err=undefined;
  token:any;
  category:any={
    name:'',
    description:''
  }
  success:any;
  error:any;
  id:any
  constructor(private _certification:CertificationsService,private router:Router,private act:ActivatedRoute,){}

  ngOnInit() {
    this.id = this.act.snapshot.paramMap.get('categoryId');
    if (this.id != undefined){
      this._certification.getCategoryById(this.id).subscribe(res=>{
        this.category=res;
      })
    }

  }
  modify(){
    this.id = this.act.snapshot.paramMap.get('categoryId');
    this._certification.updateCat(this.id,this.category).subscribe(res=>{
      this.success= "Category Updated! "
    },err=>{
      this.error= "Category Not Updated!"
    })
  }

  addCategory(){
    this._certification.addCategory(this.category)
      .subscribe(
        res=>{
          this.router.navigate(['/categories']);
        }
        ,err=> {
          this.err=err;
        }
      )
  }

}
