<%@page import="es.caib.dir3caib.utils.Configuracio"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale"%>
<%
Locale locale = Locale.getDefault();
String idioma = request.getParameter("lang");
if (idioma != null) {
	locale = new Locale(idioma);
} 

ResourceBundle messages = ResourceBundle.getBundle("es.caib.dir3caib.front.webapp.missatges", locale);
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%=locale.getLanguage()%>" lang="<%=locale.getLanguage()%>">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1">

<title><%=messages.getString("titol")%></title>

<link rel="apple-touch-icon-precomposed" href="img/favicon-apple.png" />
<link rel="icon" href="img/favicon.png" />

<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet" />

<link href="css/styles.css" rel="stylesheet" />

</head>
<body>

	<header>
		<div class="container">
			<div id="logo">
				<img src="img/logo_dir3caib.png" width="70" height="70" alt="<%=messages.getString("logoAlt")%>" /> <span><%=messages.getString("logoNom")%></span>
			</div>
			<button type="button" class="imc-bt-menu float-right" id="imc-bt-menu" title="Menú">
			 <span class="noVisible" id="menuHamburguesa"><%=messages.getString("menu.altMenu")%></span>
			</button>
		</div>
		
		<!-- menu lateral -->
		<div class="imc-marc" id="imc-marc">
            <div class="imc-marc-menu" id="imc-marc-menu">
                <div class="imc-cercador" id="imc-cercador"></div>
                <ul>
                    <li id="langs" class="imc-marc-ico imc--idioma">
                        <a href="index.jsp?lang=ca" id="idioma" data-lang="ca" lang="ca"><% if("ca".equals(locale.getLanguage())){ %><strong><%=messages.getString("menu.ca")%></strong><% } else { %><%=messages.getString("menu.ca")%><% } %></a> \ 
                        <a href="index.jsp?lang=es" id="idioma" data-lang="es" lang="es"><% if("es".equals(locale.getLanguage())){ %><strong><%=messages.getString("menu.es")%></strong><% } else { %><%=messages.getString("menu.es")%><% } %></a>
                    </li>
                    <li>
                        <a href="https://www.caib.es/govern/external/accessibilitatext.do?path=/webcaib/govern_illes/accessibilitat/accessibilitatweb&lang=<%=locale.getLanguage() %>&fitxa=3705238" class="imc-marc-ico imc--accessibilitat" id="imc-marc-accessibilitat">
                            <span><%=messages.getString("menu.accessibilitat")%></span>
                        </a>
                    </li>
                    <li>
                        <a href="#" class="imc-marc-ico imc--cerrar" id="imc-bt-cerrar"><span><%=messages.getString("menu.cerrar")%></span></a>
                    </li>
                </ul>
            </div>
        </div>
	</header>
    <div class="imc--fons"></div>
    
	<section class="imc-logo">
		<div>
			<a href="https://www.caib.es" class="imc--goib" title="<%=messages.getString("logoAlt")%>" target="_blank"> 
			     <img src="img/goib_front_lateral.png" title="" alt="<%=messages.getString("logoAlt")%>"> <span><%=messages.getString("logoAlt")%></span>
			</a>
		</div>
	</section>

	<section class="formulariCerca">

		<h3><%=messages.getString("titol")%></h3>

		<p><%=messages.getString("descripcio")%></p>

        <div class="row columnes">
			<div id="nivelAdministracionDiv">
				<label for="codigoNivelAdministracion"><%=messages.getString("form.nivelAdministracion.label")%>:</label>
				<select id="codigoNivelAdministracion">
					<option value="1"><%=messages.getString("form.nivelAdministracion.1")%></option>
					<option value="2" selected="selected"><%=messages.getString("form.nivelAdministracion.2")%></option>
					<option value="3"><%=messages.getString("form.nivelAdministracion.3")%></option>
					<option value="4"><%=messages.getString("form.nivelAdministracion.4")%></option>
					<option value="5"><%=messages.getString("form.nivelAdministracion.5")%></option>
					<option value="6"><%=messages.getString("form.nivelAdministracion.6")%></option>
				</select>
			</div>
	
			<div id="comunidadAutonomaDiv">
				<label for="codigoComunidad"><%=messages.getString("form.ccaa.label")%>:</label>
				<select id="codigoComunidad">
					<option value="0"><%=messages.getString("form.ccaa.0")%></option>
					<option value="1"><%=messages.getString("form.ccaa.1")%></option>
					<option value="2"><%=messages.getString("form.ccaa.2")%></option>
					<option value="5"><%=messages.getString("form.ccaa.5")%></option>
					<option value="6"><%=messages.getString("form.ccaa.6")%></option>
					<option value="7"><%=messages.getString("form.ccaa.7")%></option>
					<option value="8"><%=messages.getString("form.ccaa.8")%></option>
					<option value="9"><%=messages.getString("form.ccaa.9")%></option>
					<option value="18"><%=messages.getString("form.ccaa.18")%></option>
					<option value="19"><%=messages.getString("form.ccaa.19")%></option>
					<option value="13"><%=messages.getString("form.ccaa.13")%></option>
					<option value="15"><%=messages.getString("form.ccaa.15")%></option>
					<option value="10"><%=messages.getString("form.ccaa.10")%></option>
					<option value="11"><%=messages.getString("form.ccaa.11")%></option>
					<option value="12"><%=messages.getString("form.ccaa.12")%></option>
					<option value="4" selected="selected"><%=messages.getString("form.ccaa.4")%></option>
					<option value="17"><%=messages.getString("form.ccaa.17")%></option>
					<option value="16"><%=messages.getString("form.ccaa.16")%></option>
					<option value="3"><%=messages.getString("form.ccaa.3")%></option>
					<option value="14"><%=messages.getString("form.ccaa.14")%></option>
				</select>
			</div>
        </div>
        
		<div class="row" id="denominacionDiv">
			<label for="denominacion"><%=messages.getString("form.buscador.label")%>:</label>
			<input id="denominacion" type="text" autocomplete="off" />
			
			<button id="limpiar"><%=messages.getString("borrarBtn") %></button>
		</div>
		

		<div id="resultados">
			<div class="card">
				<p class="cooficial" id="cooficial"></p>
				<p class="oficial" id="oficial"></p>

				<span class="codigodir3" id="codigodir3"></span>

				<div class="columnes">
					<div class="direccio">
						<p class="titol"><%=messages.getString("resultados.direccion")%></p>
						<ul>
							<li id="direccioNom"></li>
							<li id="direccioCompl"></li>
							<li id="direccioCP"></li>
							<li id="direccioProv"></li>
							<li id="direccioPais"></li>
						</ul>
					</div>
					<div class="contactes">
						<p class="titol"><%=messages.getString("resultados.contactos")%></p>
						<ul id="contactesList"></ul>
					</div>
				</div>

				<div class="superior">
					<span class="titol"><%=messages.getString("resultados.unidadSuperior")%></span>
					<a href="#" class="enlaceUnidad" id="unitatSuperior"><span class="codi"></span>	- <span class="tag"></span>
					</a>
				</div>

				<div class="arrel">
					<span class="titol"><%=messages.getString("resultados.unidadRaiz")%></span>
					<a href="#" class="enlaceUnidad" id="unitatArrel"><span class="codi"></span> - <span class="tag"></span></a>
				</div>

			</div>

		</div>

	</section>


	<footer class="imc-peu">
		<div class="imc--contingut">
			<div class="imc-peu-govern">
				<div>
					<span>&copy; <%=messages.getString("pie.organismo")%></span> <span><%=messages.getString("pie.direccion")%></span>
				</div>
			</div>

			<div class="imc-peu-opcions">
				<ul>
					<li>
					   <a href="https://www.caib.es/seucaib/<%=locale.getLanguage() %>/mapaWeb"><%=messages.getString("pie.mapaweb")%> 
					   </a>
					</li>
					<li>
					   <a href="https://www.caib.es/seucaib/<%=locale.getLanguage() %>/fichainformativa/4266878"><%=messages.getString("pie.avisoLegal")%> 
					   <img src="img/legal.png" title="<%=messages.getString("pie.avisoLegal")%>" alt="<%=messages.getString("pie.avisoLegal")%>" /> 
					   </a>
					</li>
					<li>
					   <a href="http://www.caib.es/govern/rss.do?lang=<%=locale.getLanguage() %>" target="_blank"><%=messages.getString("pie.rss")%>
							<img src="img/rss.png" title="<%=messages.getString("pie.rss")%>" alt="<%=messages.getString("pie.rss")%>" /> 
					   </a>
					</li>
				</ul>
			</div>

			<div class="imc-peu-xarxes">
				<div>
					<p><%=messages.getString("pie.redes")%>:</p>
					<ul>
						<li><a href="http://www.youtube.com/CanalIllesBalears" title="<%=messages.getString("pie.youtube")%>" target="_blank"> 
						  <img src="img/youtube.png" title="<%=messages.getString("pie.youtube")%>" alt="<%=messages.getString("pie.youtube")%>" />
						</a></li>
						<li><a href="https://www.facebook.com/GovernIllesBalears/" title="<%=messages.getString("pie.facebook")%>" target="_blank"> 
						  <img src="img/facebook.png" title="<%=messages.getString("pie.facebook")%>" alt="<%=messages.getString("pie.facebook")%>" />
						</a></li>
						<li><a href="https://www.instagram.com/goib" title="<%=messages.getString("pie.instagram")%>" target="_blank"> 
							<img src="img/instagram.png" title="<%=messages.getString("pie.instagram")%>" alt="<%=messages.getString("pie.instagram")%>" />
						</a></li>
						<li><a href="http://www.twitter.com/goib" title="<%=messages.getString("pie.twitter")%>" target="_blank">
								<img src="img/twitter.png"	title="<%=messages.getString("pie.twitter")%>" alt="<%=messages.getString("pie.twitter")%>" />
						</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>

	<script type="text/javascript" src="js/jquery-3-7-1-min.js"></script>
	<script type="text/javascript" src="js/jquery-autocomplete.js"></script>
	<script type="module"> 

        $(function () {
            'use strict';

            let codigoComunidad = "4"; 
            let nivelAdministracion = "2";
            let unidades = []; 

            let nombreFichero = "obtener?nivel=" + nivelAdministracion + "&comunidad=" + codigoComunidad;
            $.getScript( nombreFichero, function( data, textStatus, jqxhr ) {

                        unidades = $.map(unitats, function (value, key) { 
                    return { value: value.denominacionCooficial + " - " + value.codigo, 
                             data: {
                                dir3: value.codigo,
                                denominacion: value.denominacion,
                                cooficial: value.denominacionCooficial, 
                                esEdp: value.esEdp,
                                nif: value.nif,
                                raiz_codigo: value.unidadRaiz.codigo,
                                raiz_denominacion: value.unidadRaiz.denominacion,
                                raiz_cooficial: value.unidadRaiz.denominacionCooficial,
                                sup_codigo: value.unidadSuperior.codigo,
                                sup_denominacion: value.unidadSuperior.denominacion,
                                sup_cooficial: value.unidadSuperior.denominacionCooficial,
                                comunidad: value.direccion.comunidadAutonoma, 
                                pais: value.direccion.pais,
                                provincia: value.direccion.provincia,
                                cp: value.direccion.codigoPostal,
                                complemento: value.direccion.complemento,
                                numero: value.direccion.numVia,
                                nombre: value.direccion.nombreVia,
                                tipo: value.direccion.tipoVia,
                                localidad: value.direccion.localidad,
                                contactos: value.contactos,
                             } 
                            }; 
                        });
              });

             $('#codigoComunidad').on('change', function(){
                codigoComunidad = this.value;
                $('#denominacion').val('');
                
                if (codigoComunidad != 'undefined' && nivelAdministracion != 'undefined'){
                    let nombreFichero = "obtener?nivel=" + nivelAdministracion + "&comunidad=" + codigoComunidad;

                    $.getScript( nombreFichero, function( data, textStatus, jqxhr ) {

                        unidades = $.map(unitats, function (value, key) { 
                    return { value: value.denominacionCooficial + " - " + value.codigo, 
                             data: {
                                dir3: value.codigo,
                                denominacion: value.denominacion,
                                cooficial: value.denominacionCooficial, 
                                esEdp: value.esEdp,
                                nif: value.nif,
                                raiz_codigo: value.unidadRaiz.codigo,
                                raiz_denominacion: value.unidadRaiz.denominacion,
                                raiz_cooficial: value.unidadRaiz.denominacionCooficial,
                                sup_codigo: value.unidadSuperior.codigo,
                                sup_denominacion: value.unidadSuperior.denominacion,
                                sup_cooficial: value.unidadSuperior.denominacionCooficial,
                                comunidad: value.direccion.comunidadAutonoma, 
                                pais: value.direccion.pais,
                                provincia: value.direccion.provincia,
                                cp: value.direccion.codigoPostal,
                                complemento: value.direccion.complemento,
                                numero: value.direccion.numVia,
                                nombre: value.direccion.nombreVia,
                                tipo: value.direccion.tipoVia,
                                localidad: value.direccion.localidad,
                                contactos: value.contactos,
                             } 
                            }; 
                        });
                    });                

                    $('#denominacion').devbridgeAutocomplete({
                        lookup: unidades,
                        lookupLimit: 10,
                        nocache: true,
                        formatResult: function (suggestion, currentValue) {
                            if (suggestion.data.denominacion != suggestion.data.cooficial)
                                return "<p>"+suggestion.value+"<br><em>"+suggestion.data.cooficial+"</em></p>";
                            else
                                return "<p>"+suggestion.value+"</p>";
                        },
                        minChars: 3,
                        onSelect: function (suggestion) {
                            mostrarResultados(unidades, suggestion.data.dir3);
                        },
                        showNoSuggestionNotice: true,
                        noSuggestionNotice: '<%=messages.getString("form.sincoincidencias")%>',
                        groupBy: 'comunidad'
                    });

                }

            });
            
            $('#codigoNivelAdministracion').on('change', function(){
                nivelAdministracion = this.value;
                $('#denominacion').val('');
                // $('#denominacion').devbridgeAutocomplete('clear');

                if (codigoComunidad != 'undefined' && nivelAdministracion != 'undefined'){
                    let nombreFichero = "obtener?nivel=" + nivelAdministracion + "&comunidad=" + codigoComunidad;

                    $.getScript( nombreFichero, function( data, textStatus, jqxhr ) {

                        unidades = $.map(unitats, function (value, key) { 
                    return { value: value.denominacionCooficial + " - " + value.codigo, 
                             data: {
                                dir3: value.codigo,
                                denominacion: value.denominacion,
                                cooficial: value.denominacionCooficial, 
                                esEdp: value.esEdp,
                                nif: value.nif,
                                raiz_codigo: value.unidadRaiz.codigo,
                                raiz_denominacion: value.unidadRaiz.denominacion,
                                raiz_cooficial: value.unidadRaiz.denominacionCooficial,
                                sup_codigo: value.unidadSuperior.codigo,
                                sup_denominacion: value.unidadSuperior.denominacion,
                                sup_cooficial: value.unidadSuperior.denominacionCooficial,
                                comunidad: value.direccion.comunidadAutonoma, 
                                pais: value.direccion.pais,
                                provincia: value.direccion.provincia,
                                cp: value.direccion.codigoPostal,
                                complemento: value.direccion.complemento,
                                numero: value.direccion.numVia,
                                nombre: value.direccion.nombreVia,
                                tipo: value.direccion.tipoVia,
                                localidad: value.direccion.localidad,
                                contactos: value.contactos,
                             } 
                            }; 
                        });
                    });                 

                    $('#denominacion').devbridgeAutocomplete({
                        lookup: unidades,
                        lookupLimit: 10,
                        nocache: true,
                        formatResult: function (suggestion, currentValue) {
                            if (suggestion.data.denominacion != suggestion.data.cooficial)
                                return "<p>"+suggestion.value+"<br><em>"+suggestion.data.cooficial+"</em></p>";
                            else
                                return "<p>"+suggestion.value+"</p>"; 
                        },
                        minChars: 3,
                        onSelect: function (suggestion) {
                            mostrarResultados(unidades, suggestion.data.dir3);
                        },
                        showNoSuggestionNotice: true,
                        noSuggestionNotice: '<%=messages.getString("form.sincoincidencias")%>',
                        groupBy: 'comunidad'
                    });

                }

            });

            $('#denominacion').on('focus', function(){           
                if (codigoComunidad != 'undefined' && nivelAdministracion != 'undefined'){
                    let nombreFichero = "obtener?nivel=" + nivelAdministracion + "&comunidad=" + codigoComunidad;

                    $.getScript( nombreFichero, function( data, textStatus, jqxhr ) {

                        unidades = $.map(unitats, function (value, key) { 
                    return { value: value.denominacionCooficial + " - " + value.codigo, 
                             data: {
                                dir3: value.codigo,
                                denominacion: value.denominacion,
                                cooficial: value.denominacionCooficial, 
                                esEdp: value.esEdp,
                                nif: value.nif,
                                raiz_codigo: value.unidadRaiz.codigo,
                                raiz_denominacion: value.unidadRaiz.denominacion,
                                raiz_cooficial: value.unidadRaiz.denominacionCooficial,
                                sup_codigo: value.unidadSuperior.codigo,
                                sup_denominacion: value.unidadSuperior.denominacion,
                                sup_cooficial: value.unidadSuperior.denominacionCooficial,
                                comunidad: value.direccion.comunidadAutonoma, 
                                pais: value.direccion.pais,
                                provincia: value.direccion.provincia,
                                cp: value.direccion.codigoPostal,
                                complemento: value.direccion.complemento,
                                numero: value.direccion.numVia,
                                nombre: value.direccion.nombreVia,
                                tipo: value.direccion.tipoVia,
                                localidad: value.direccion.localidad,
                                contactos: value.contactos,
                             } 
                            }; 
                        });
                    });

                    $('#denominacion').devbridgeAutocomplete('clear');                    

                    $('#denominacion').devbridgeAutocomplete({
                        lookup: unidades,
                        lookupLimit: 10,
                        nocache: true,
                        formatResult: function (suggestion, currentValue) {
                            if (suggestion.data.cooficial != suggestion.data.denominacion)
                                return "<p>"+suggestion.value+"<br><em>"+suggestion.data.cooficial+"</em></p>";
                            else
                                return "<p>"+suggestion.value+"</p>";
                        },
                        minChars: 3,
                        onSelect: function (suggestion) {
                            mostrarResultados(unidades, suggestion.data.dir3);
                        },
                        showNoSuggestionNotice: true,
                        noSuggestionNotice: '<%=messages.getString("form.sincoincidencias")%>',
                        groupBy: 'comunidad'
                    });

                }
            }); 

            $('#unitatArrel').on('click', function() {
                mostrarResultados (unidades, $('#unitatArrel').attr('data-dir3'));
            }); 

            $('#unitatSuperior').on('click', function() {
                mostrarResultados (unidades, $('#unitatSuperior').attr('data-dir3'));
            });

            $('#limpiar').on('click', function() {
                $('#denominacion').val('');
                $('#denominacion').devbridgeAutocomplete('clear');
                $('.card').css('display', 'none');
            }); 

            $('#imc-bt-menu').on('click', function(){
                $('#imc-marc').css('display', 'block');
                $('.imc--fons').css('display', 'block');
            });

            $('.imc--fons').on('click', function() {
                $('#imc-marc').css('display', 'none');
                $('.imc--fons').css('display', 'none');
            });

            $('#imc-bt-cerrar').on('click', function() {
                $('#imc-marc').css('display', 'none');
                $('.imc--fons').css('display', 'none');
            });

        });
        
        function mostrarResultados (unidades, codigoDir3) {
            let organismo;
            
            unidades.forEach( function (item){  
                    if (item.data.dir3 == codigoDir3) {
                        return organismo = item;
                    }
            });

            if (organismo != 'undefined') {

                if(organismo.data.cooficial != organismo.data.denominacion){
                    $('#cooficial').html(organismo.data.cooficial);
                    $('#oficial').html(organismo.data.denominacion);
                }else{
                    $('#oficial').html("");
                    $('#cooficial').html(organismo.data.denominacion);
                }
                
                $('#codigodir3').html(organismo.data.dir3);

                $('#direccioNom').html(organismo.data.tipo + " " + organismo.data.nombre + ", " + organismo.data.numero);
                $('#direccioCompl').html(organismo.data.complemento);
                $('#direccioCP').html(organismo.data.cp + " - " + organismo.data.localidad);

                const provincia = (organismo.data.provincia != null) ? organismo.data.provincia + " - " : "";
               
                $('#direccioProv').html(provincia + organismo.data.comunidad);
                $('#direccioPais').html(organismo.data.pais);

                $('#contactesList li').remove();
                let contactosLista = organismo.data.contactos;
                if(contactosLista.length > 0) {
                    for (let i = 0; i < contactosLista.length; i++) {

                        let tipoContacto = "";

                        switch (contactosLista[i].tipo) {
                            case 'C':
                                tipoContacto = "<%=messages.getString("tipoContacto.C")%>";
                                break;
                            case 'E':
                                tipoContacto = "<%=messages.getString("tipoContacto.E")%>";
                                break;
                            case 'F':
                                tipoContacto = "<%=messages.getString("tipoContacto.F")%>";
                                break;
                            case 'O':
                                tipoContacto = "<%=messages.getString("tipoContacto.O")%>";
                                break;
                            case 'P':
                                tipoContacto = "<%=messages.getString("tipoContacto.P")%>";
                                break;
                            case 'T':
                                tipoContacto = "<%=messages.getString("tipoContacto.T")%>";
                                break;
                            case 'U':
                                tipoContacto = "<%=messages.getString("tipoContacto.U")%>";
                                break;
                            default:
                                tipoContacto = "<%=messages.getString("tipoContacto")%>";
                                break;   
                        }

                         $('#contactesList').append('<li><span class="tag">'+ tipoContacto + ':</span> '+ contactosLista[i].valor +'</li>');
                    }
            }

            if (organismo.data.raiz_codigo !== organismo.data.dir3){
                $('#unitatSuperior').attr('data-dir3', organismo.data.sup_codigo); 
                $('#unitatSuperior .codi').html(organismo.data.sup_codigo);
                $('#unitatSuperior .tag').html(organismo.data.sup_denominacion);
            
                $('#unitatArrel').data('dir3', organismo.data.raiz_codigo);
                $('#unitatArrel .codi').html(organismo.data.raiz_codigo);
                $('#unitatArrel .tag').html(organismo.data.raiz_denominacion);

                $('.superior').css('display','block');
                $('.arrel').css('display','block');
            }else{
                $('.superior').css('display','none');
                $('.arrel').css('display','none');
            }

            $('.card').css('display','block');

            }
        }

    </script>
 <% if(Configuracio.isCAIB() && !Configuracio.isDevelopment()){ %>
    <script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-91918552-2', 'auto'
    
    , {'clientId': '858F3BE17D90B9408200C5E2D3EDF47A'}
    
     );
  
  ga('send', 'pageview');
</script>
<% } %>
</body>
</html>
