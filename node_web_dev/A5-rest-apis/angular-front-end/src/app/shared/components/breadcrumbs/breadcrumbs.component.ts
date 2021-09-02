import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.scss']
})
export class BreadcrumbsComponent implements OnInit {
  @Input() breadcrumbs: NameValuePair[];

  constructor() { }

  get breadcrumbsExceptLast(): NameValuePair[] {
    return this.breadcrumbs.slice(0, this.breadcrumbs.length - 1);
  }

  get lastBreadCrumb(): NameValuePair {
    return this.breadcrumbs.slice(this.breadcrumbs.length - 1, this.breadcrumbs.length)[0];
  };

  ngOnInit() {
  }

}


interface NameValuePair {
  name: String,
  value: String
}