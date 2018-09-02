import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Student } from './data-verification/student.model';

@Injectable()
export class RestService {

  constructor(private http: Http) {}

  login(idStudent: number) {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    return this.http.get('/api/poll/user/' + idStudent, { headers })
    .pipe(
        map((response: Response) => response.json())
    );
  }

  getStudentData(idStudent: number, idPoll: number) {
  const headers = new Headers({ 'Content-Type': 'application/json' });
  return this.http.get('/api/poll/userData/' + idStudent + '/' + idPoll, { headers })
  .pipe(
      map((response: Response) => response.json())
  );
}

updateStudentData(studentData: Student) {
  console.log(studentData);
  const headers = new Headers({ 'Content-Type': 'application/json' });
  return this.http.post('/api/poll/userData', studentData, { headers })
}


}
