import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { SharedModule, TreeNode } from 'primeng/api';
import { TreeTableModule } from 'primeng/treetable';
import { CommonModule } from '@angular/common';
import { ApiResponse } from '../../services/api.service'; // Importa la interfaz



@Component({
  selector: 'app-tree-table',
  imports: [SharedModule, TreeTableModule, CommonModule],
  templateUrl: './tree-table.component.html',
  styleUrls: ['./tree-table.component.css']
})
export class TreeTableComponent implements OnInit {
  files: TreeNode[] = [];
  cols: any[] = [];

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getData().subscribe((data: ApiResponse) => {
      this.files = this.formatDataToTree(data);
      this.cols = [
        { field: 'name', header: 'Name' },
        { field: 'type', header: 'Type' },
        { field: 'value', header: 'Value' }
      ];
    });
  }

  // Transforma los datos de la API en un formato compatible con TreeTable
  formatDataToTree(data: ApiResponse): TreeNode[] {
    const nodes: TreeNode[] = [];
  
    for (const [key, value] of Object.entries(data)) {
      const node: TreeNode = {
        data: {
          name: key,
          type: 'API'
        },
        children: this.processObject(value)
      };
  
      nodes.push(node);
    }
  
    return nodes;
  }
  
  // Función auxiliar para procesar objetos recursivamente
  processObject(obj: any): TreeNode[] {
    const nodes: TreeNode[] = [];
  
    for (const [key, value] of Object.entries(obj)) {
      const node: TreeNode = {
        data: {
          name: key,
          type: Array.isArray(value) ? 'Array' : typeof value
        },
        children: []
      };
  
      if (typeof value === 'object' && value !== null) {
        if (Array.isArray(value)) {
          // Si es un array, procesa cada elemento
          node.children = value.map((item, index) => ({
            data: {
              name: `[${index}]`,
              type: typeof item
            },
            children: this.processObject(item)
          }));
        } else {
          // Si es un objeto, procesa recursivamente
          node.children = this.processObject(value);
        }
      } else {
        // Si no es un objeto, muestra el valor directamente
        node.data.value = value;
      }
  
      nodes.push(node);
    }
  
    return nodes;
  }
}