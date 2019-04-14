package back;

//import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
//import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class NavigationControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	//this managed property will read value from request parameter pageId
    @ManagedProperty(value = "#{param.pageId}")
    private String pageId;

    //condional navigation based on pageId
    //if pageId is 1 show page1.xhtml,
    //if pageId is 2 show page2.xhtml
    //else show home.xhtml

   public String showPage() {/*
	   ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	   String targetURL = ec.getRequestParameterMap().get("targetURL");*/
	   
	   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("[DEBUG] pageId: " + pageId));/*
	   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("[DEBUG] targetURL: " + targetURL));
	   try {
		  ec.redirect(targetURL);
	   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }*/
   
	   if(pageId == null) {
		   return "../index";
	   }
		 
	   if(pageId.equals("loginPage")) {
		   return "login";
	   } else if(pageId.equals("indexPage")) {
		   return "../index";
	   } else {
		   return "../index";
	   }
	}
}
