function FileFrame(fileArea,  bean , botao , img) {
	var self = this;

	this.fileArea = fileArea;
	this.botao = botao;
	this.img = img ;
	
	this.bean = bean;

	this.init = function() {
		
		this.fileArea.addEventListener("dragleave", self.dragHover, false);
		self.fileArea.addEventListener("dragover", self.dragHover, false);
		self.fileArea.addEventListener("drop", self.drop, false);

	};

	this.dragHover = function(e) {
		// Impede possíveis tratamentos dos arquivos
		// arrastados pelo navegador, por exemplo, exibir
		// o conteudo do mesmo.
		e.stopPropagation();
		e.preventDefault();

		// Quando o arquivo está sobre área alteramos o seu estilo
		self.fileArea.className = (e.type == "dragover" ? "hover" : "");
		
	};

	this.drop = function(e) {
		self.dragHover(e);

		self.file = e.dataTransfer.files[0];

		if (self.file.type.match('image.*')) {
			self.read(self.file);
			self.sendFile(self.file , self.bean);
			
			
		}
	};

	this.read = function(file) {
	    
	   
	      //var reader = new FileReader();

	      //reader.onload = function(f) {
	        //self.fileArea.innerHTML = "";
	        //self.fileArea.setAttribute("style", "padding: 0px !important;");
	        
	        //var img = document.getElementById("*tag_imagem");
	        //img.setAttribute("src", f.target.result);
	        var spam =  document.getElementById("drop-message");
	        spam.setAttribute("style", "display: none;");
	        img.setAttribute("style", "display: inline-block;  margin-right: 30%; margin-left: 30%; margin-top: 9%; margin-bottom: 20%; ");
	        self.fileArea.setAttribute("style", "background-color: #FFFFFF; ");
	        //img.setAttribute("id", "tag_imagem");

	        //self.fileArea.appendChild(img);
	    //  };

	     // reader.readAsDataURL(file);
	    };
	
	
	this.sendFile = function(file , bean) {

		var f = new FormData();
		
		f.enctype="multipart/form-data";

		f.append("file", file);

		var request = new XMLHttpRequest();
		request.open("POST", "/memoriavirtual/uploadarquivo/"+"bean"+ bean, true);
		request.send(f);
		request.onreadystatechange = function() {
			// Término do envio do formulário
			if(request.readyState==4 ){
				if( request.status == 201) {
							
					window.setTimeout(self.voltar, 1000);
					
				}
		    }
		};
	};
	this.voltar = function(){
        var spam =  document.getElementById("drop-message");
        spam.setAttribute("style", "display: inline-block;");
        img.setAttribute("style", "display: none; ");
		self.fileArea.setAttribute("style", "background-color: #F7F7F7;");
		self.botao.click();
		self.botao.click();
		self.botao.click();
		self.botao.click();
		self.botao.click();
		self.botao.click();
	};
}
iniciarComponenteUpload = (function(bean , botaoid , idImagem ) {
	// Recupera a div que conterá a imagem
	//alert(idImagem);
	var area = document.getElementById("image-area");
	var botao = document.getElementById(botaoid);
	 var img = document.getElementById(idImagem);
	var fileFrameArea = new FileFrame(area ,bean , botao  , img);
	
	fileFrameArea.init();
	
});