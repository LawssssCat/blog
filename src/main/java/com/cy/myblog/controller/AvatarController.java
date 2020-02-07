package com.cy.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.myblog.common.vo.JsonResult;
import com.cy.myblog.pojo.po.Avatar;
import com.cy.myblog.service.AvatarService;

@RequestMapping("/avatar")
@Controller
public class AvatarController extends BaseController {

	@Autowired
	private AvatarService avatarService;
	
	@ResponseBody
	@RequestMapping("/{id}")
	public JsonResult findObjectById(@PathVariable("id") Long id)  {
		Avatar avatar = avatarService.doFindObjectById(id) ;
		return new JsonResult(OK,"success",avatar);
	}
}
