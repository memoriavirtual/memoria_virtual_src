function FileFrame(fileArea,  bean , botao) {
	var self = this;

	this.fileArea = fileArea;
	this.botao = botao;
	
	
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
			self.sendFile(self.file , self.bean);
			self.botao.click();
		}
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
			if(request.readyState==4 && request.status == 201) {
				
		    }
		};
	};

}
init = (function(bean , botaoid ) {
	// Recupera a div que conterá a imagem
	var area = document.getElementById("image-area");
	var botao = document.getElementById(botaoid+":"+botaoid);
	var fileFrameArea = new FileFrame(area ,bean , botao );

	fileFrameArea.init();
	
});