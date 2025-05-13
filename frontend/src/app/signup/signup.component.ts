
  import { HttpClient } from '@angular/common/http';
  import { Component, inject } from '@angular/core';
  import { FormsModule, NgForm } from '@angular/forms';
  import { AccountService } from '../services/account.service';
  import { ToastComponent } from '../toast/toast.component';
  import { RouterLink } from '@angular/router';
  import { CommonModule } from '@angular/common';
  
  @Component({
    selector: 'app-signup',
    standalone: true,
    imports: [RouterLink, FormsModule, ToastComponent, CommonModule],
    templateUrl: './signup.component.html',
    styleUrl: './signup.component.css'
  })
  export class SignupComponent {
    accountService = inject(AccountService);
    toastHeading = ""; 
    toastDescription = ""; 
    toastVisible = false;
    showPassword: boolean = false;
    onSubmit(form: NgForm) {
      Object.keys(form.controls).forEach(key => {
        const control = form.controls[key];
        control.markAsTouched();
      });
  
      if (form.valid) {
        this.accountService.createAccount(form.value)
          .subscribe({
            next: res => {
              console.log(res);
              this.generateToast("Success", "Account created");
              form.reset();
            },
            error: err => {
              console.log(err);
              const error = err.error;
              this.generateToast(error['title'], error['detail'])
            }
          });
      } else {
        this.generateToast("Error", "Please fix the validation errors");
      }
    }
  
    generateToast(heading: string, description: string) {
      this.toastHeading = heading;
      this.toastDescription = description;
      this.toastVisible = true;
  
      setTimeout(() => {
        this.toastVisible = false;
      }, 5000);
    }
  }