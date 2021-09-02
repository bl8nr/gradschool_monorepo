import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';

import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  alertText: String;
  isAlerted: Boolean = false;
  isLoggingIn: Boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) { }

  handleLoginClicked(formValues) {
    this.isLoggingIn = true;
    this.userService.login(formValues).subscribe(() => {
      this.isLoggingIn = false;
      this.router.navigate(['/home']);
    }, (err) => {
      console.log(err);
      this.isLoggingIn = false;
      if (err.status === 401) {
        this.isAlerted = true;
        this.alertText = 'Incorrect username or password';
      } else {
        this.isAlerted = true;
        // this.alertText = 'Server Error';
        this.alertText = 'Incorrect username or password';
      }
    });
  }

  ngOnInit() {
    this.isAlerted = false;

    this.loginForm = this.formBuilder.group({
      username: ['', /* validations */],
      password: ['', /* validations */],
      rememberMe: [true, /* no validations needed */]
    });

  }

}
