//script responsavel por fazer o load de cada documento dentro do div CaixaCorpo, basta 
//criar o documento e fazer as alteraçoes, mas basicamente eh esse script (AJAX)

function loadbempatrimonial()
{
var xmlhttp;

xmlhttp=new XMLHttpRequest();

xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status == 200)
    {
    document.getElementById("CaixaCorpo").innerHTML=xmlhttp.responseText;
    }
  else
	{
	document.getElementById("CaixaCorpo").innerHTML=xmlhttp.readyState;
	}
  }
xmlhttp.open("GET","WEB-INF/bempatrimonial.html",true);
xmlhttp.send();
}
