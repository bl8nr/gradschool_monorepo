import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { BreadcrumbsComponent } from './breadcrumbs.component';

describe('BreadcrumbsComponent', () => {
  let component: BreadcrumbsComponent;
  let fixture: ComponentFixture<BreadcrumbsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [ BreadcrumbsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BreadcrumbsComponent);
    component = fixture.componentInstance;
    component.breadcrumbs = [
      { name: 'name 1', value: '/value1'},
      { name: 'name 2', value: '/value2'},
      { name: 'name 3', value: '/value3'} ];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return all but the last breadcrumb on breadcrumbsExceptLast', () => {
    expect(component.breadcrumbsExceptLast.length).toEqual(2);
    expect(component.breadcrumbsExceptLast[0].name).toEqual('name 1');
    expect(component.breadcrumbsExceptLast[0].value).toEqual('/value1');
    expect(component.breadcrumbsExceptLast[1].name).toEqual('name 2');
    expect(component.breadcrumbsExceptLast[1].value).toEqual('/value2');
  });

  it('should return only the last breadcrumb on lastBreadCrumb', () => {
    expect(component.lastBreadCrumb.name).toEqual('name 3');
    expect(component.lastBreadCrumb.value).toEqual('/value3');
  });

  it('should only have a last breadcrumb when fewer than 2 breadcrumbs exist', () => {
    component.breadcrumbs = [
      { name: 'name 2', value: '/value2'}
    ];
    expect(component.breadcrumbsExceptLast.length).toEqual(0);
    expect(component.lastBreadCrumb.name).toEqual('name 2');
    expect(component.lastBreadCrumb.value).toEqual('/value2');
  })
});
