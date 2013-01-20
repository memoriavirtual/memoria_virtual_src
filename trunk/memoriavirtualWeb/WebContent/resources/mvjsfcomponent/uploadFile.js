function FileFrame(fileArea,  bean , botao , img, typeFile ) {
	var self = this;

	this.fileArea = fileArea;
	this.botao = botao;
	this.img = img ;
	this.typeFile = typeFile;
	this.bean = bean;
	var div1 = document.getElementById("example-content");
	this.div = div1;
	this.init = function() {
		
		this.fileArea.addEventListener("dragleave", self.dragHover, false);
		self.fileArea.addEventListener("dragover", self.dragHover, false);
		self.fileArea.addEventListener("drop", self.drop, false);
		window.document.addEventListener("dragover", self.melhoraPosicao, false);
		window.document.addEventListener("dragleave", self.melhoraPosicao, false);
		window.document.addEventListener("drop", self.melhoraPosicao, false);
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
	
	this.melhoraPosicao = function(e) {
		e.preventDefault();
		
		
	};
	
	this.drop = function(e) {
		self.dragHover(e);
		
		for(var i = 0 ; i < e.dataTransfer.files.length ; i++){
			self.file = e.dataTransfer.files[i];
			alert(self.file.type);
			if (self.file.type.match(self.typeFile)) {
				self.read(self.file);
				self.sendFile(self.file , self.bean);
			}
		}
	
		
	};

	this.read = function(file) {
	    
	   
	      // var reader = new FileReader();

	      // reader.onload = function(f) {
	        // self.fileArea.innerHTML = "";
	        // self.fileArea.setAttribute("style", "padding: 0px !important;");
	        
	        // var img = document.getElementById("*tag_imagem");
	        // img.setAttribute("src", f.target.result);
	        var spam =  document.getElementById("drop-message");
	        spam.setAttribute("style", "display: none;");
	        img.setAttribute("style", "display: inline-block;  margin-right: 40%; margin-left: 40%; margin-top: 20%; margin-bottom: 20%; ");
	        self.fileArea.setAttribute("style", "background-color: #FFFFFF; ");
	        // img.setAttribute("id", "tag_imagem");

	        // self.fileArea.appendChild(img);
	    // };

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
	};
}
iniciarComponenteUpload = (function(bean , botaoid , idImagem , typeFile ) {
	// Recupera a div que conterá a imagem
	// alert(idImagem);
	var area = document.getElementById("image-area");
	var botao = document.getElementById(botaoid);
	 var img = document.getElementById(idImagem);
	var fileFrameArea = new FileFrame(area ,bean , botao  , img, typeFile);
	
	fileFrameArea.init();
	
});