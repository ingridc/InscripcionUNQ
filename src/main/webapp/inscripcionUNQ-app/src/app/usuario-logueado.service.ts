import { Injectable} from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class UsuarioLogueadoService  {
  private usuarioLogueado = new BehaviorSubject<boolean>(false);
  hayUsuarioLogueado = this.usuarioLogueado.asObservable();
  constructor() { }

 notificarUsuarioLoguedado() {
    this.usuarioLogueado.next(true);
 }
}
