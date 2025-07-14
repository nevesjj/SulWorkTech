import { importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

export const appConfig = {
  providers: [
    importProvidersFrom(BrowserModule, FormsModule, HttpClientModule),
    provideRouter(routes)
  ]
};


