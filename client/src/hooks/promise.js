import { useEffect, useRef, useState } from "react";
import { fetchData, handleError, https } from "../apiUtils";

function getDefaultHelpers() {
  return {
    onSuccess: data => {},
    onError: error => {},
    afterRun: () => {},
  };
}

export function useMutation() {
  let [value, setValue] = useState(null);
  let [error, setError] = useState(null);

  async function run(promise, helpers = getDefaultHelpers()) {
    try {
      let data = await promise();
      setValue(data);
      if (helpers.onSuccess) helpers.onSuccess(data);
    } catch (err) {
      handleError(err, setError);
      if (helpers.onError) helpers.onError(err);
    } finally {
      if (helpers.afterRun) helpers.afterRun();
    }
  }

  return {
    value,
    setValue,
    error,
    run,
  };
}

export function useQuery(url, helpers = getDefaultHelpers()) {
  let [value, setValue] = useState(null);
  let [error, setError] = useState(null);
  let reRun = useRef();

  reRun.current = async () => {
    try {
      let data = await fetchData(url, https.GET);
      setValue(data);
      if (helpers.onSuccess) helpers.onSuccess(data);
    } catch (err) {
      handleError(err, setError);
      if (helpers.onError) helpers.onError(err);
    } finally {
      if (helpers.afterRun) helpers.afterRun();
    }
  };

  function run() {
    reRun.current();
  }

  useEffect(() => {
    reRun.current();
  }, []);

  return {
    value,
    setValue,
    error,
    run,
  };
}
