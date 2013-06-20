function wyswygPapelDeTrabajo(){

        dojo.require('dijit.TitlePane');

        tinyMCE.init({
            mode : "exact",
            language : "es",
            elements : " _contenido_id",
            theme : "advanced",
            plugins : "fullscreen,table,emotions,spellchecker,advhr,insertdatetime,preview,paste",
            theme_advanced_buttons1 : "bold,italic,underline,|,justifyleft,justifycenter,justifyright,fontselect,fontsizeselect,formatselect,|,removeformat,|,forecolor,backcolor,|,fullscreen",
            theme_advanced_buttons2 : "cut,copy,paste,|,bullist,numlist,|,outdent,indent,|,undo,redo,|,tablecontrols,|,hr,removeformat,visualaid",
            theme_advanced_buttons3 : "",
            theme_advanced_toolbar_location : "top",
            theme_advanced_toolbar_align : "left",
            skin : "o2k7",
            skin_variant : "silver",
            
            //fullscreen_new_window : true,
            fullscreen_settings : {
                theme_advanced_path_location : "top"
            }            

        });

        
            
        
            
        $(function() {
            
            $( ".accordion" ).accordion({

                collapsible: true,
                active: false,
                autoHeight: false
            });
            
            $("#botonEditarContenido").click(
            //Si hago click en editar, activo boton guardar, activo el textarea y desactivo editar
            function ()
            {

                $("#contenidoText").hide();
                $("#contenidoTextArea").show();
                $("#botonEditarContenido").attr("disable",true);
                $("#botonEditarContenido").attr("style","background-color: inherit; font-weight: 100; font-size: smaller");
                $("#proceedContenido").removeAttr("style");
                $("#proceedContenido").removeAttr("disabled");
                //$("#proceed").attr("style","visibility: visible");
                $("#_contenido_id").removeAttr("disabled");
            });
            
            
            
           /* $("#botonEditar").click(
            //Si hago click en editar, activo guardar, activo el textarea y desactivo editar
            function ()
            {

                //toggleReadOnly(tinyMCE.get('_conclusion_id'));
                $("#botonEditar").attr("disable",true);
                $("#botonEditar").attr("style","background-color: inherit; font-weight: 100; font-size: smaller");

                $("#proceed").removeAttr("style");
                $("#proceed").removeAttr("disabled");
                //$("#proceed").attr("style","visibility: visible");
                $("#_conclusion_id").removeAttr("disabled");
            }

            );*/

        });

}


function wyswygConclusion(){
        dojo.require('dijit.TitlePane');

        tinyMCE.init({
            mode : "exact",
            language : "es",
            elements : " _conclusion_id,  _redaccion_id ",
            theme : "advanced",
            plugins : "fullscreen,table,emotions,spellchecker,advhr,insertdatetime,preview,paste",
            theme_advanced_buttons1 : "bold,italic,underline,|,justifyleft,justifycenter,justifyright,fontselect,fontsizeselect,formatselect,|,removeformat,|,forecolor,backcolor,|,fullscreen",
            theme_advanced_buttons2 : "cut,copy,paste,|,bullist,numlist,|,outdent,indent,|,undo,redo,|,tablecontrols,|,hr,removeformat,visualaid",
            theme_advanced_buttons3 : "",
            theme_advanced_toolbar_location : "top",
            theme_advanced_toolbar_align : "left",
            skin : "o2k7",
            skin_variant : "silver",

            //fullscreen_new_window : true,
            fullscreen_settings : {
                theme_advanced_path_location : "top",


            }

        });


        $(function() {

            $( ".accordion" ).accordion({

                collapsible: true,
                active: false,
                autoHeight: false
            });
            /*$("#botonEditar").click(
            //Si hago click en editar, activo guardar, activo el textarea y desactivo editar
            function ()
            {

                toggleReadOnly(tinyMCE.get('_conclusion_id'));
                $("#botonEditar").attr("disable",true);
                $("#botonEditar").attr("style","background-color: inherit; font-weight: 100; font-size: smaller");

                $("#proceed").removeAttr("style");
                $("#proceed").removeAttr("disabled");
                //$("#proceed").attr("style","visibility: visible");
                $("#_conclusion_id").removeAttr("disabled");
            }

        );*/

            $("#botonEditarRedaccion").click(
            //Si hago click en editar, activo guardar, activo el textarea y desactivo editar
            function ()
            {

                $("#conclusionText").hide();
                $("#conclusionTextArea").show();
                $("#botonEditarRedaccion").attr("disable",true);
                $("#botonEditarRedaccion").attr("style","background-color: inherit; font-weight: 100; font-size: smaller");
                $("#proceedRedaccion").removeAttr("style");
                $("#proceedRedaccion").removeAttr("disabled");
                //$("#proceed").attr("style","visibility: visible");
                $("#_redaccion_id").removeAttr("disabled");
            }

        );

        });


        



}

function wyswygObservacion(){
        dojo.require('dijit.TitlePane');

        tinyMCE.init({
            mode : "exact",
            language : "es",
            elements : " _conclusion_id, _redaccion_id ",
            theme : "advanced",
            plugins : "fullscreen,table,emotions,spellchecker,advhr,insertdatetime,preview,paste",
            theme_advanced_buttons1 : "bold,italic,underline,|,justifyleft,justifycenter,justifyright,fontselect,fontsizeselect,formatselect,|,removeformat,|,forecolor,backcolor,|,fullscreen",
            theme_advanced_buttons2 : "cut,copy,paste,|,bullist,numlist,|,outdent,indent,|,undo,redo,|,tablecontrols,|,hr,removeformat,visualaid",
            theme_advanced_buttons3 : "",
            theme_advanced_toolbar_location : "top",
            theme_advanced_toolbar_align : "left",
            skin : "o2k7",
            skin_variant : "silver",


            //fullscreen_new_window : true,
            fullscreen_settings : {
                theme_advanced_path_location : "top"
                

            }

        });

                   
        

        $(function() {
                
            $( ".accordion" ).accordion({

                collapsible: true,
                active: false,
                autoHeight: false
            });
            $("#botonEditar").click(
            //Si hago click en editar, activo guardar, activo el textarea y desactivo editar
            function ()
            {

                $("#conclusionText").hide();
                $("#conclusionTextArea").show();
                $("#botonEditar").attr("disable",true);
                $("#botonEditar").attr("style","background-color: inherit; font-weight: 100; font-size: smaller");
                        
                $("#proceed").removeAttr("style");
                $("#proceed").removeAttr("disabled");
                //$("#proceed").attr("style","visibility: visible");
                $("#_conclusion_id").removeAttr("disabled");
            }
                    
        );

            $("#botonEditarRedaccion").click(
            //Si hago click en editar, activo guardar, activo el textarea y desactivo editar
            function ()
            {

                $("#redaccionText").hide();
                $("#redaccionTextArea").show();
                $("#botonEditarRedaccion").attr("disable",true);
                $("#botonEditarRedaccion").attr("style","background-color: inherit; font-weight: 100; font-size: smaller");
                $("#proceedRedaccion").removeAttr("style");
                $("#proceedRedaccion").removeAttr("disabled");
                //$("#proceed").attr("style","visibility: visible");
                $("#_redaccion_id").removeAttr("disabled");
            }

        );

        });


        
        

}