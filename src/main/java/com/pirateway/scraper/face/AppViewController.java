package com.pirateway.scraper.face;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@URLMappings(mappings = {
        @URLMapping(
                id = "main",
                pattern = "/",
                viewId = "/WEB-INF/jsf/index.xhtml"),
        @URLMapping(
                id = "error",
                pattern = "/error",
                viewId = "/WEB-INF/jsf/errorPage.xhtml"),
        @URLMapping(
                id = "userList",
                pattern = "/user/list",
                viewId = "/WEB-INF/jsf/user/userList.xhtml"),
        @URLMapping(
                id = "userEdit",
                pattern = "/user/edit",
                viewId = "/WEB-INF/jsf/user/userEdit.xhtml"),
        @URLMapping(
                id = "registration",
                pattern = "/registration",
                viewId = "/WEB-INF/jsf/registration.xhtml"),
        @URLMapping(
                id = "forkList",
                pattern = "/fork/list",
                viewId = "/WEB-INF/jsf/fork/forkList.xhtml")})
public class AppViewController {
}


