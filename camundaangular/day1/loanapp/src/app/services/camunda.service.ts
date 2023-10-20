import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
//import { ZBClient } from 'zeebe-node'

@Injectable({
  providedIn: 'root'
})
export class CamundaService {

  constructor(private httpClient:HttpClient) { }

  public createProcessInstance():Observable<any>{
    /*const zbc = new ZBClient({
      // loglevel: 'DEBUG',
      camundaCloud: {
        clientId: "dRCjx_6~YCbnKSDOcmpw_DuNdf5sUdRY",
        clientSecret: "gXpG8CM7LSaV4obM2H3i72hP_1ut8Ay5Z6VfsY3aAZUgIdOt0drUBPagpxYveh-_",
        clusterId: "67517168-7d3d-4011-b2f4-89ac5e79bde9",
        clusterRegion: "dsm-1",
      },
    });
    zbc.createProcessInstance('Process_1wc4mx2', {}).then(console.log);*/
   return  this.httpClient.get("http://localhost:3000/start");
  }

}
