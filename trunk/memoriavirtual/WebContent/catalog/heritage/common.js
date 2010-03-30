/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function setshw(par, lay, size) {
  var obj = document.getElementById(setshw.arguments[1]);
  if(navigator.appName.substring(0,9)=="Microsoft")
  {
    obj.style.left = event.x - event.offsetX + document.body.scrollLeft;
    obj.style.top  = event.y - event.offsetY + document.body.scrollTop + size;
  }
  else
  {
    obj.style.left = par.offsetLeft + "px";
    obj.style.top  = par.offsetTop + size + "px";
  }
  obj.style.display ='block';
}
function shwlay(lay)
{
  var obj = document.getElementById(shwlay.arguments[0]);
  obj.style.display='block';
}
function hidlay(lay)
{
  var obj = document.getElementById(hidlay.arguments[0]);
  obj.style.display='none';
}

function insertHidden () {

}
