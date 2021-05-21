import { helper } from '@ember/component/helper';

export default helper(function formatDate(params) {
  let longData, formatType, formattedDate;
  [ longData, formatType ] = params;

  formattedDate = new Date(Number(longData));

  if (Number(formatType)) {
    return formattedDate.toLocaleString();
  }

  return formattedDate.toLocaleDateString();

});
