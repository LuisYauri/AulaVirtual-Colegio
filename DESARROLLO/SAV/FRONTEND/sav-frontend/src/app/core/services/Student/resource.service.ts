import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../../services/auth.service";
import {SendComment, SendRate} from "../../model/Student/resource.model";

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  private API_URL_COMMENT = `${environment.apiMain}comentarios/`
  private API_URL_RATE = `${environment.apiMain}calificaciones/`

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  private headersList() {
    return {
      headers: {
        Authorization: `Bearer ${this.authService.getToken()}`
      }
    };
  }

  postComment(data: SendComment) {
    return this.http.post(`${this.API_URL_COMMENT}`, data, this.headersList())
  }

  postRate(data: SendRate) {
    return this.http.post(`${this.API_URL_RATE}`, data, this.headersList())
  }
}
