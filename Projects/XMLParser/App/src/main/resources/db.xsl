<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/inhabitants">
        <html>
            <body>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ПІБ</th>
                            <th>Вік</th>
                            <th>Факультет</th>
                            <th>Кафедра</th>
                            <th>Курс</th>
                            <th>Адреса</th>
                            <th>Проплата</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="inhabitant">
                            <tr>
                                <td>
                                    <xsl:value-of select="fullname"/>
                                </td>
                                <td>
                                    <xsl:value-of select="age"/>
                                </td>
                                <td>
                                    <xsl:value-of select="faculty"/>
                                </td>
                                <td>
                                    <xsl:value-of select="cathedra"/>
                                </td>
                                <td>
                                    <xsl:value-of select="grade"/>
                                </td>
                                <td>
                                    <xsl:value-of select="homeplace"/>
                                </td>
                                <td>
                                    <xsl:value-of select="payment"/>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>