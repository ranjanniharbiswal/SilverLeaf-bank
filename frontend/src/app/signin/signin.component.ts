import { Component, inject, OnInit, HostListener } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ToastComponent } from '../toast/toast.component';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [RouterLink, FormsModule, ToastComponent],
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.css',
})
export class SigninComponent implements OnInit {
  accountService = inject(AccountService);
  router = inject(Router);
  toastHeading = '';
  toastDescription = '';
  toastVisible = false;
  ngOnInit(): void {
    this.clearTokenIfPresent();
  }
  @HostListener('window:load', ['$event'])
  onPageLoad(event: Event) {
    this.clearTokenIfPresent();
  }
  clearTokenIfPresent(): void {
    if (localStorage.getItem('token')) {
      localStorage.removeItem('token');
      console.log(
        'Token removed due to page load/refresh or navigation to login.'
      );
    }
  }
  onLogin(form: NgForm) {
    if (form.valid) {
      this.accountService.loginAccount(form.value).subscribe({
        next: (res) => {
          form.reset();
          const response = res as any;
          localStorage.setItem('token', response.token);
          this.router.navigate(['dashboard']);
        },
        error: (err) => {
          this.generateToast('Failure', 'Unauthorized access');
        },
      });
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
