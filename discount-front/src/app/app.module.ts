import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CartComponent } from './discount/cart.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
 
@NgModule({
    declarations: [CartComponent, AppComponent],
    imports: [BrowserModule, HttpClientModule, FormsModule],
    bootstrap: [AppComponent]
})
export class AppModule { }