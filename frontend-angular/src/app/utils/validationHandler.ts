import {HttpErrorResponse} from '@angular/common/http';
import {FormGroup} from '@angular/forms';

export function validationHandler(error: Error, form: FormGroup) {
  if (error instanceof HttpErrorResponse && error.status === 400) {
    const errorObject = JSON.parse(error.error);
    for (const errorFromServer of errorObject.fieldErrors) {
      const formControl = form.get(errorFromServer.field);
      if (formControl) {
        formControl.setErrors({serverError: errorFromServer.message});
      }
    }
  }
}
