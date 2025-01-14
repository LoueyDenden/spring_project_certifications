import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {OrdersService} from "../../services/orders.service";
import {CertificationsService} from "../../services/certifications.service";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent implements OnInit{
  constructor(private _certification:CertificationsService,private _auth:AuthService,private _orders:OrdersService) {
  }
  username?:'';
  error:any;
  certificationsWillPurchase:any=[];
  totalAmount:any;
  success:any
  selectedPaymentMethod: any = undefined;  // Will hold the selected payment method (VISA, MASTERCARD, PAYPAL)
  formattedCardNumber: string = '';  // Formatted card number for display
  cardNumber: string = '';  // Store card number as plain string
  result:any;
  id:any;
  order:any= {
    reference:'',
    paymentMethod:'',
    username:'',
    certifications:[{
      certificationId:''
    }]
  };

  ngOnInit() {
    if(this._auth.isLoggedIn()){
      this.username=this._auth.getUserDataFromToken().sub
    }else{
      this.username=undefined
    }

    this.certificationsWillPurchase = this._certification.getCartItems();
    this.totalAmount = this.certificationsWillPurchase.reduce(
      (sum: any, certification: { price: any; }) => sum + (certification.price || 0),
      0
    );
  }
  removeGame(id: any) {
    this._certification.removeFromCart(id)
    this.certificationsWillPurchase=this._certification.getCartItems()
    this.totalAmount = this.certificationsWillPurchase.reduce(
      (sum: any, certification: { price: any; }) => sum + (certification.price || 0),
      0
    );
  }
  // Format the card number as the user types
  onCardNumberChange(event: string): void {
    // Remove all non-digit characters
    let value = event.replace(/\D/g, '');

    // Format it as XXXX-XXXX-XXXX-XXXX
    if (value.length > 4) {
      value = value.replace(/(\d{4})(?=\d)/g, '$1-');
    }

    this.cardNumber = value;  // Update raw card number
    this.formattedCardNumber = value;  // Update formatted card number for display
  }
checkout(){
 this.order.reference=`MS-${new Date().getFullYear()}${(new Date().getMonth() + 1).toString().padStart(2, '0')}${new Date().getDate().toString().padStart(2, '0')}`;
 this.order.paymentMethod=this.selectedPaymentMethod;
 this.order.username=this.username;
  this.order.certifications = this.certificationsWillPurchase.map((certification: { id: any; }) => {
    return { certificationId: certification.id }; // Assuming each certification object has a certificationId field
  });
  this._orders.createOrder(this.order).subscribe(res=>{
    this.result=res;
    this.id= this.result.msg.substring(this.result.msg.indexOf(":") + 2);
    if (this.result.msg=="certification already exists with id: "+this.id){
       // Get the part after the colon
      this.error = "You Have Already certification with ID: " + this.id+" Please remove it from your cart! ";
    }else{
      this.success = "You Have Successfully Purchased your certifications check your inventory"
      this.clearGames()
    }

  },err=>{
    console.log(err)
    }
  )
}
  clearGames(){
    this._certification.clearFromCart()
    this.certificationsWillPurchase=this._certification.getCartItems()
    this.totalAmount=0
  }


}
