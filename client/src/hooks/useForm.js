import { useState } from "react";

export default function useForm(initialValues) {
  let [values, setValues] = useState(initialValues);

  function handleChange(e) {
    setValues({
      ...values,
      [e.target.name]: e.target.value,
    });
  }

  function resetForm() {
    setValues(initialValues);
  }

  return {
    values,
    setValues,
    handleChange,
    resetForm,
  };
}
