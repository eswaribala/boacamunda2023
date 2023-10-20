import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {ProcessComponent} from "./process/process.component";
import {StartComponent} from "./start/start.component";
import {TaskComponent} from "./task/task.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'processlist', component: ProcessComponent },
  { path: 'startprocess/:processdefinitionkey', component: StartComponent },
  { path: 'tasklist', component: TaskComponent },
  { path: 'tasklist/:id', component: TaskComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
