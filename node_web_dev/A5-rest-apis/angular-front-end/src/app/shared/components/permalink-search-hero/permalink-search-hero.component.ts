import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-permalink-search-hero',
  templateUrl: './permalink-search-hero.component.html',
  styleUrls: ['./permalink-search-hero.component.scss']
})
export class PermalinkSearchHeroComponent implements OnInit {
  searchForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder
  ) { }

  handleGoClicked() {
    console.log(this.searchForm.value.permalink);
  }

  ngOnInit() {
    this.searchForm = this.formBuilder.group({
      permalink: ['', /* no validations */ ]
    });
  }

}
