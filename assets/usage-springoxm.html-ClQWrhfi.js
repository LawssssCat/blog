import{_ as c,r,o as k,c as p,a as d,b as n,w as i,d as s,e as a}from"./app-WYhW1Tbc.js";const h={},B=s("p",null,"spring 为 jaxb, jibx, xstream 这些封装提供了统一的接口。",-1),y=s("div",{class:"language-java line-numbers-mode","data-ext":"java","data-title":"java"},[s("pre",{class:"shiki shiki-themes github-light one-dark-pro",style:{"background-color":"#fff","--shiki-dark-bg":"#282c34",color:"#24292e","--shiki-dark":"#abb2bf"},tabindex:"0"},[s("code",null,[s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"package"),s("span",{style:{color:"#24292E","--shiki-dark":"#C678DD"}}," org.example"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"import"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," org.junit.jupiter.api.Assertions"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"import"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," org.junit.jupiter.api.Test"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"import"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," org.springframework.oxm.xstream.XStreamMarshaller"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"import"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," javax.xml.transform.Result"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"import"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," javax.xml.transform.stream.StreamResult"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"import"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," java.io."),s("span",{style:{color:"#005CC5","--shiki-dark":"#E5C07B"}},"*"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"import"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," java.util.Objects"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"public"),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," class"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#E5C07B"}}," OxmTest"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}}," {")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"    final"),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," static"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," File"),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}}," FILE "),s("span",{style:{color:"#D73A49","--shiki-dark":"#56B6C2"}},"="),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," new"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}}," File"),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}},"("),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"Objects"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"requireNonNull"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"("),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"OxmTest"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"class"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"getResource"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"("),s("span",{style:{color:"#032F62","--shiki-dark":"#98C379"}},'"/"'),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"))."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"getFile"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"(),")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"            OxmTest"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"class"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"getName"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"()"),s("span",{style:{color:"#D73A49","--shiki-dark":"#56B6C2"}}," +"),s("span",{style:{color:"#032F62","--shiki-dark":"#98C379"}},' ".xml"'),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}},")"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"    @"),s("span",{style:{color:"#D73A49","--shiki-dark":"#E5C07B"}},"Test")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"    void"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}}," test"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"()"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}}," {")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"        XStreamMarshaller"),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}}," xStreamMarshaller"),s("span",{style:{color:"#D73A49","--shiki-dark":"#56B6C2"}}," ="),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," new"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}}," XStreamMarshaller"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"();")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"        Person"),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}}," person"),s("span",{style:{color:"#D73A49","--shiki-dark":"#56B6C2"}}," ="),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," new"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}}," Person"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"();")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"        person"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"setId"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"("),s("span",{style:{color:"#032F62","--shiki-dark":"#98C379"}},'"111"'),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},");")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"        person"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"setName"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"("),s("span",{style:{color:"#032F62","--shiki-dark":"#98C379"}},'"222"'),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},");")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"        person"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"setAge"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"("),s("span",{style:{color:"#005CC5","--shiki-dark":"#D19A66"}},"333"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},");")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"        try"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}}," ("),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"OutputStream"),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}}," outputStream"),s("span",{style:{color:"#D73A49","--shiki-dark":"#56B6C2"}}," ="),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," new"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}}," FileOutputStream"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"(FILE)) {")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"            Result"),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}}," result"),s("span",{style:{color:"#D73A49","--shiki-dark":"#56B6C2"}}," ="),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," new"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}}," StreamResult"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"(outputStream);")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"            xStreamMarshaller"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"marshal"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"(person, result);")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"        } "),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"catch"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}}," ("),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"FileNotFoundException"),s("span",{style:{color:"#E36209","--shiki-dark":"#E06C75","font-style":"inherit","--shiki-dark-font-style":"italic"}}," e"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},") {")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"            throw"),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," new"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}}," RuntimeException"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"(e);")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"        } "),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"catch"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}}," ("),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"IOException"),s("span",{style:{color:"#E36209","--shiki-dark":"#E06C75","font-style":"inherit","--shiki-dark-font-style":"italic"}}," e"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},") {")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"            throw"),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," new"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}}," RuntimeException"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"(e);")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"        }")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"        Assertions"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"assertTrue"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"("),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}},"FILE"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"."),s("span",{style:{color:"#6F42C1","--shiki-dark":"#61AFEF"}},"exists"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"());")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"    }")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"}")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"})])]),s("div",{class:"line-numbers","aria-hidden":"true"},[s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"})])],-1),E=s("div",{class:"language-java line-numbers-mode","data-ext":"java","data-title":"java"},[s("pre",{class:"shiki shiki-themes github-light one-dark-pro",style:{"background-color":"#fff","--shiki-dark-bg":"#282c34",color:"#24292e","--shiki-dark":"#abb2bf"},tabindex:"0"},[s("code",null,[s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"package"),s("span",{style:{color:"#24292E","--shiki-dark":"#C678DD"}}," org.example"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"import"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," lombok.Data"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"import"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," javax.xml.bind.annotation.XmlRootElement"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"@"),s("span",{style:{color:"#D73A49","--shiki-dark":"#E5C07B"}},"Data")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"@"),s("span",{style:{color:"#D73A49","--shiki-dark":"#E5C07B"}},"XmlRootElement")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"public"),s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}}," class"),s("span",{style:{color:"#6F42C1","--shiki-dark":"#E5C07B"}}," Person"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}}," {")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"    private"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," String"),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}}," id"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"    private"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," String"),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}}," name"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#D73A49","--shiki-dark":"#C678DD"}},"    private"),s("span",{style:{color:"#24292E","--shiki-dark":"#E5C07B"}}," Integer"),s("span",{style:{color:"#24292E","--shiki-dark":"#E06C75"}}," age"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},";")]),a(`
`),s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"}")]),a(`
`),s("span",{class:"line"}),a(`
`),s("span",{class:"line"})])]),s("div",{class:"line-numbers","aria-hidden":"true"},[s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"}),s("div",{class:"line-number"})])],-1),m=s("div",{class:"language-xml line-numbers-mode","data-ext":"xml","data-title":"xml"},[s("pre",{class:"shiki shiki-themes github-light one-dark-pro",style:{"background-color":"#fff","--shiki-dark-bg":"#282c34",color:"#24292e","--shiki-dark":"#abb2bf"},tabindex:"0"},[s("code",null,[s("span",{class:"line"},[s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"<"),s("span",{style:{color:"#22863A","--shiki-dark":"#E06C75"}},"org.example.Person"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"><"),s("span",{style:{color:"#22863A","--shiki-dark":"#E06C75"}},"id"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},">111</"),s("span",{style:{color:"#22863A","--shiki-dark":"#E06C75"}},"id"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"><"),s("span",{style:{color:"#22863A","--shiki-dark":"#E06C75"}},"name"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},">222</"),s("span",{style:{color:"#22863A","--shiki-dark":"#E06C75"}},"name"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"><"),s("span",{style:{color:"#22863A","--shiki-dark":"#E06C75"}},"age"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},">333</"),s("span",{style:{color:"#22863A","--shiki-dark":"#E06C75"}},"age"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},"></"),s("span",{style:{color:"#22863A","--shiki-dark":"#E06C75"}},"org.example.Person"),s("span",{style:{color:"#24292E","--shiki-dark":"#ABB2BF"}},">")]),a(`
`),s("span",{class:"line"})])]),s("div",{class:"line-numbers","aria-hidden":"true"},[s("div",{class:"line-number"})])],-1);function A(F,u){const o=r("RepoLink"),t=r("Tabs");return k(),p("div",null,[B,d(" more "),n(o,{path:"code/demo-java-xml/n13-springoxm-usage/test/java/org/example/"}),n(t,{id:"6",data:[{id:"测试"},{id:"实体类"},{id:"输出"}]},{title0:i(({value:l,isActive:e})=>[a("测试")]),title1:i(({value:l,isActive:e})=>[a("实体类")]),title2:i(({value:l,isActive:e})=>[a("输出")]),tab0:i(({value:l,isActive:e})=>[y]),tab1:i(({value:l,isActive:e})=>[E]),tab2:i(({value:l,isActive:e})=>[m]),_:1})])}const D=c(h,[["render",A],["__file","usage-springoxm.html.vue"]]),b=JSON.parse('{"path":"/zh/dev-java-xml/usage-springoxm.html","title":"使用 XML 工具： spring-oxm","lang":"zh-CN","frontmatter":{"title":"使用 XML 工具： spring-oxm","date":"2024-04-14T00:00:00.000Z","tag":["xml","java","spring"],"order":44,"gitInclude":["../../../code/demo-java-xml/n13-springoxm-usage/src/test/java/org/example/OxmTest.java","../../../code/demo-java-xml/n13-springoxm-usage/src/main/java/org/example/Person.java"],"description":"spring 为 jaxb, jibx, xstream 这些封装提供了统一的接口。","head":[["meta",{"property":"og:url","content":"https://lawsssscat.github.io/blog/zh/blog/zh/dev-java-xml/usage-springoxm.html"}],["meta",{"property":"og:site_name","content":"个人博客"}],["meta",{"property":"og:title","content":"使用 XML 工具： spring-oxm"}],["meta",{"property":"og:description","content":"spring 为 jaxb, jibx, xstream 这些封装提供了统一的接口。"}],["meta",{"property":"og:type","content":"article"}],["meta",{"property":"og:locale","content":"zh-CN"}],["meta",{"property":"og:updated_time","content":"2024-05-05T14:49:19.000Z"}],["meta",{"property":"article:author","content":"Steven"}],["meta",{"property":"article:tag","content":"xml"}],["meta",{"property":"article:tag","content":"java"}],["meta",{"property":"article:tag","content":"spring"}],["meta",{"property":"article:published_time","content":"2024-04-14T00:00:00.000Z"}],["meta",{"property":"article:modified_time","content":"2024-05-05T14:49:19.000Z"}],["script",{"type":"application/ld+json"},"{\\"@context\\":\\"https://schema.org\\",\\"@type\\":\\"Article\\",\\"headline\\":\\"使用 XML 工具： spring-oxm\\",\\"image\\":[\\"\\"],\\"datePublished\\":\\"2024-04-14T00:00:00.000Z\\",\\"dateModified\\":\\"2024-05-05T14:49:19.000Z\\",\\"author\\":[{\\"@type\\":\\"Person\\",\\"name\\":\\"Steven\\",\\"url\\":\\"https://github.com/LawssssCat/\\"}]}"]]},"headers":[],"git":{"createdTime":1713186909000,"updatedTime":1714920559000,"contributors":[{"name":"lawsssscat","email":"18041500+LawssssCat@users.noreply.github.com","commits":2}]},"readingTime":{"minutes":0.27,"words":81},"filePathRelative":"zh/dev-java-xml/usage-springoxm.md","localizedDate":"2024年4月14日","excerpt":"<p>spring 为 jaxb, jibx, xstream 这些封装提供了统一的接口。</p>\\n","autoDesc":true}');export{D as comp,b as data};