import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registrationForm: FormGroup;
  alertText: String;
  isAlerted: Boolean = false;
  isCreatingAccount: Boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) { }

  handleSignupClicked(formValues) {
    // register returns the user but dont use it unless your debugging
    // user the cached user object in user.service, hit it directly it should be ominpresent
    this.isCreatingAccount = true;
    this.userService.register(formValues).subscribe((user) => {
      this.isCreatingAccount = false;
      this.router.navigate(['/home']);
    }, (err) => {
      this.isCreatingAccount = false;
      this.isAlerted = true;
      this.alertText = err.json();
    });
  }

  ngOnInit() {
    this.isAlerted = false;
    this.isCreatingAccount = false;

    this.registrationForm = this.formBuilder.group({
      username: ['', Validators.required ],
      firstName: ['', Validators.required ],
      lastName: ['', Validators.required ],
      email: ['', Validators.required ],
      password: ['', Validators.required ]
    });

  }

}
