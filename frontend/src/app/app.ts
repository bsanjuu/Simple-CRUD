import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from './services/employee.service';
import { Employee } from './employee';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent implements OnInit {
  employees: Employee[] = [];
  newEmployee: Employee = { name: '', age: 0, department: '' };

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.employeeService.getAllEmployees()
      .subscribe(data => this.employees = data);
  }

  addEmployee(): void {
    this.employeeService.createEmployee(this.newEmployee)
      .subscribe(emp => {
        this.employees.push(emp);
        this.newEmployee = { name: '', age: 0, department: '' };
      });
  }
}
