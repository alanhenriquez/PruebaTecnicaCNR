import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProyectosService {
  private apiUrl = 'http://localhost:8080'; // URL base del backend

  constructor(private http: HttpClient) {}

  // Obtener todos los proyectos combinados
  obtenerProyectosCombinados(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/proyectos-combinados`);
  }

  // Obtener un proyecto combinado por ID
  obtenerProyectoPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/proyectos-combinados/${id}`);
  }

  // Crear un nuevo proyecto combinado
  crearProyecto(proyecto: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/proyectos-combinados`, proyecto);
  }

  // Actualizar un proyecto combinado existente
  actualizarProyecto(id: number, proyecto: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/proyectos-combinados/${id}`, proyecto);
  }

  // Eliminar un proyecto combinado por ID
  eliminarProyecto(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/proyectos-combinados/${id}`);
  }

  // Obtener todos los detalles de un proyecto específico
  obtenerDetallesDeProyecto(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/proyecto-detalles?codigoProyecto=${id}`);
  }

  // Crear un nuevo detalle para un proyecto específico
  crearDetalle(detalle: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/proyecto-detalles`, detalle);
  }

  // Actualizar un detalle existente
  actualizarDetalle(id: number, detalle: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/proyecto-detalles/${id}`, detalle);
  }

  // Eliminar un detalle por ID
  eliminarDetalle(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/proyecto-detalles/${id}`);
  }
}