<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="windows-1251"/>
    <xsl:template match="/">
        <html>
            <body>
                <table border="1">
                    <tr>
                        <th>Name</th>
                        <th>Faculty</th>
                        <th>Department</th>
                        <th>Position</th>
                        <th>Salary</th>
                        <th>TimeInOffice</th>
                    </tr>
                    <xsl:for-each select="Scientists/Scientist">
                        <tr>
                            <td>
                                <xsl:value-of select="Name"/>
                            </td>
                            <td>
                                <xsl:value-of select="Faculty"/>
                            </td>
                            <td>
                                <xsl:value-of select="Department"/>
                            </td>
                            <td>
                                <xsl:value-of select="Position"/>
                            </td>
                            <td>
                                <xsl:value-of select="Salary"/>
                            </td>
                            <td>
                                <xsl:value-of select="TimeInOffice"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>