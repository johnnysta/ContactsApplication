import {HttpErrorResponse} from '@angular/common/http';
import {FormGroup} from '@angular/forms';

export function validationHandler(error: Error, form: FormGroup) {
  if (error instanceof HttpErrorResponse && error.status === 400) {
    Object.keys(error.error).forEach(key => {
      const formControl = form.get(key);
      if (formControl) {
        formControl.setErrors({serverError: error.error[key]});
      }
    });
  }
}
