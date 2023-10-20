import { Component, OnInit } from '@angular/core';
import {CamundaService} from "../services/camunda.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private  camundaService:CamundaService) {

  }

  ngOnInit(): void {

    this.camundaService.createProcessInstance().subscribe(response=>{
      console.log(response);
    })
  }

}
