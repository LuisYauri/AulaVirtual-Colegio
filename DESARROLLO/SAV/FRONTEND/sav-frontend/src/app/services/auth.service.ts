import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {AulaDataToken, Login, PayloadDataToken, PayloadToken} from "../model/auth.model";
import * as jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private API_URL_LOGIN = `${environment.apiMain}login`
  private API_URL_LOGIN_DATA = `${environment.apiMain}usuarios/`
  private payload: PayloadToken
  private payloadData: PayloadDataToken
  private payloadAula: AulaDataToken

  constructor(private http: HttpClient) {
  }

  postLogin(data: Login) {
    return this.http.post(this.API_URL_LOGIN, data)
  }

  getLoginData(username: string) {
    return this.http.get(`${this.API_URL_LOGIN_DATA}${username}`)
  }

  isLoggedIn() {
    return !!(localStorage.getItem('access_token') && localStorage.getItem('data_username') && localStorage.getItem('username'));
  }

  logout() {
    localStorage.removeItem('access_token');
    localStorage.removeItem('data_username');
    localStorage.removeItem('username');
    localStorage.removeItem('data_aula');
  }

  getTokenDecode() {
    try {
      const token = localStorage.getItem('access_token')
      return jwt_decode(token);
    } catch (e) {

    }
  }

  getUsername() {
    this.payload = this.getTokenDecode()
    return this.payload.sub.toString()
  }

  getAula() {
    this.payloadAula = JSON.parse(localStorage.getItem('data_aula'))
    return this.payloadAula
  }

  getTokenDataUsername() {
    return JSON.parse(localStorage.getItem('data_username'))
  }

  getDataUsername() {
    this.payloadData = this.getTokenDataUsername()
    return this.payloadData
  }
}
