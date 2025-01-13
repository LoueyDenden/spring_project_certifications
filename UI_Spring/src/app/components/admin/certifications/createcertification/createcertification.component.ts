import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CertificationsService} from "../../../../services/certifications.service";

@Component({
  selector: 'app-createcertification',
  templateUrl: './createcertification.component.html',
  styleUrl: './createcertification.component.css'
})
export class CreatecertificationComponent {
  err=undefined;
  categories:any;
  token:any;
  success:any;
  error:any;
  certification:any={
    name:'',
    description:'',
    avaiblity:'',
    price:'',
    categoryId:''
  }
  id:any
  constructor(private _certification:CertificationsService,private router:Router,  private act: ActivatedRoute){}

  ngOnInit() {
    this.id = this.act.snapshot.paramMap.get('gameId');
    if (this.id == undefined){
      this._certification.getCategory().subscribe(
        res=>{
          this.categories=res
          // console.log(this.categories)
          // console.log(res)
        },
        err=>{
          // console.log(err)
        }
      )
    }else {
      this._certification.getCategory().subscribe(
        res=>{
          this.categories=res
          // console.log(this.categories)
          // console.log(res)
        },
        err=>{
          // console.log(err)
        }
      )
      this._certification.getCertificationById(this.id).subscribe(res=>{
        this.certification=res;
        console.log(this.certification)
      })
    }
  }

  image:any=undefined;
  select(e:any){
    this.image=e.target.files[0];
  }
  modify(){
    this._certification.modifyCertificationById(this.id,this.certification).subscribe(res=>{
      this.success= "Certification Updated! "
    },err=>{
      this.error= "Certification Not Updated!"
    })
  }
  addCertification(){
    let fd=new FormData()
    fd.append('name',this.certification.name)
    fd.append('description',this.certification.description)
    fd.append('avaiblity',this.certification.avaiblity.toString())
    fd.append('price',this.certification.price.toString())
    fd.append('categoryId',this.certification.categoryId.toString())
    //fd.append('data',JSON.stringify(this.certification))
    fd.append('file',this.image)
    console.log(this.certification)
    console.log(fd)
    this._certification.postCertification(fd)
      .subscribe(
        res=>{
          this.router.navigate(['/home']);
        }
        ,err=> {
          this.err=err;
        }
      )
  }
}
