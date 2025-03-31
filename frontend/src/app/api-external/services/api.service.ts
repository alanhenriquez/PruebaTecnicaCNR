import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'https://api.apis.guru/v2/list.json';

  constructor(private http: HttpClient) {}

  getData(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.apiUrl);
  }
}



// Define the interfaces for the API response =====================================================



export interface ApiVersion {
  added: string;
  info: {
    contact: {
      email: string;
      name: string;
      url: string;
    };
    description: string;
    title: string;
    version: string;
    'x-apisguru-categories': string[];
    'x-logo': {
      backgroundColor: string;
      url: string;
    };
    'x-origin': Array<{
      format: string;
      url: string;
      version: string;
    }>;
    'x-providerName': string;
  };
  updated: string;
  swaggerUrl: string;
  swaggerYamlUrl: string;
  openapiVer: string;
  link: string;
}

export interface ApiDataEntry {
  added: string;
  preferred?: string;
  versions: {
    [version: string]: ApiVersion;
  };
}

export interface ApiResponse {
  [key: string]: ApiDataEntry;
}