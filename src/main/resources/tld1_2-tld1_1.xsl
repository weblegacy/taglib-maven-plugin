<?xml version="1.0" encoding="UTF-8"?>
<!--
  This file was initially developed in the displaytag project - http://displaytag.sourceforge.net
  Original author of this file is Andy Pruitt
-->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"
    doctype-system="http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd"
    doctype-public="-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN"/>

  <xsl:template match="//taglib">
    <taglib>
      <tlibversion>
        <xsl:value-of select="tlib-version"/>
      </tlibversion>
      <jspversion>
        <xsl:value-of select="jsp-version"/>
      </jspversion>
      <shortname>
        <xsl:value-of select="short-name"/>
      </shortname>
      <uri>
        <xsl:value-of select="uri"/>
      </uri>
      <info>
        <xsl:value-of select="description"/>
      </info>
      <xsl:apply-templates select="//tag"/>
    </taglib>
  </xsl:template>

  <xsl:template match="tag">
    <tag>
      <name>
        <xsl:value-of select="name"/>
      </name>
      <tagclass>
        <xsl:value-of select="tag-class"/>
      </tagclass>
      <xsl:if test="tei-class">
        <teiclass>
          <xsl:value-of select="tei-class"/>
        </teiclass>
      </xsl:if>
      <bodycontent>
        <xsl:value-of select="body-content"/>
      </bodycontent>
      <info>
        <xsl:value-of select="description"/>
      </info>
      <xsl:apply-templates select="attribute"/>
    </tag>
  </xsl:template>

  <xsl:template match="attribute">
    <attribute>
      <xsl:if test="description">
        <xsl:comment>
          <xsl:value-of select="description"/>
        </xsl:comment>
      </xsl:if>
      <name>
        <xsl:value-of select="name"/>
      </name>
      <required>
        <xsl:value-of select="required"/>
      </required>
      <rtexprvalue>
        <xsl:value-of select="rtexprvalue"/>
      </rtexprvalue>
    </attribute>
  </xsl:template>

</xsl:stylesheet>