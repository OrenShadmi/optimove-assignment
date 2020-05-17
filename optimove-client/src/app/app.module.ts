import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FoodContComponent } from './food-cont/food-cont.component';
import { HttpClientModule } from '@angular/common/http';
import { ChartComponent } from './chart/chart.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FoodContComponent,
    ChartComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
