import Service from '@ember/service';

export default class CookieService extends Service {
  getCookie = function(name) {
    const cookieArr = document.cookie.split(";");
    for(var i = 0; i < cookieArr.length; i++) {
      const cookiePair = cookieArr[i].split("=");
        if(name == cookiePair[0].trim()) {
            return decodeURIComponent(cookiePair[1]);
        }
    }
  };
}