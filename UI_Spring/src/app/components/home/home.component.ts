import { Component, OnInit } from '@angular/core';
import { CertificationsService } from '../../services/certifications.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  certifications: any[] = [];
  imageUrls: string[] = [];
  searchForm: FormGroup;
  name: any;
  itemsShowCount = 5;
  page = 0;
  size = 5;
  totalElements = 0;
  currentPage = 1;
  totalPages: number[] = [];
  sortField: string = 'defaultField';
  sortDir: string = 'asc';
  gameAdded: any;

  constructor(
    private _certifications: CertificationsService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.searchForm = this.fb.group({
      name: ['']
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.currentPage = +params['pageNum'] || 1;
      this.sortField = params['sortField'] || 'createdAt';
      this.sortDir = params['sortDir'] || 'asc';
      this.search();
    });
  }
  search(): void {
    this.name = this.searchForm.get('name')?.value;
    this._certifications.findByName(this.name, this.currentPage - 1, this.size).subscribe(
      (res) => {
        this.certifications = res.content;
        this.totalElements = res.totalElements;
        this.totalPages = Array.from({ length: res.totalPages }, (_, i) => i + 1);
        this.fetchImages(); // Fetch images after the certifications are loaded
      },
      (err) => {
        console.error('Error fetching data:', err);
      }
    );
  }

  fetchImages(): void {
    this.imageUrls = []; // Reset imageUrls array
    this.certifications.forEach(certification => {
      this._certifications.GetCertificationsImages(certification.id).subscribe(
        (blob) => {
          const imageUrl = URL.createObjectURL(blob);
          this.imageUrls.push(imageUrl); // Store the image URL
        },
        (err) => {
          console.error('Error fetching image:', err);
        }
      );
    });
  }

  onItemsUpdated(count: number): void {
    this.itemsShowCount = count;
    this.size = count;
    this.search();
  }

  changePage(pageNumber: number): void {
    if (pageNumber >= 1 && pageNumber <= this.totalPages.length) {
      this.currentPage = pageNumber;
      this.router.navigate([], {
        relativeTo: this.route,
        queryParams: { pageNum: pageNumber, sortField: this.sortField, sortDir: this.sortDir },
        queryParamsHandling: 'merge'
      });
      this.search();
    }
  }

  addToCart(certification: any): void {
    this._certifications.addToCart(certification);
    this.gameAdded = `${certification.name} added to cart.`;
  }
}
