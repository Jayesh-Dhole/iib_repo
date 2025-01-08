<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- Define the output format -->
    <xsl:output encoding="UTF-8" indent="yes" method="xml"></xsl:output>

    <!-- Template to match the root element 'employees' -->
    <xsl:template match="/employees">
        <!-- Root element of the output XML -->
        <company>
            <!-- Loop through each 'employee' element -->
            <xsl:for-each select="employee">
                <staff>
                    <!-- Map 'name' to 'fullName' in the output -->
                    <fullName><xsl:value-of select="name"></xsl:value-of></fullName>
                    <!-- Map 'position' to 'jobTitle' -->
                    <jobTitle><xsl:value-of select="position"></xsl:value-of></jobTitle>
                    <!-- Map 'age' to 'yearsOld' -->
                    <yearsOld><xsl:value-of select="age"></xsl:value-of></yearsOld>
                </staff>
            </xsl:for-each>
        </company>
    </xsl:template>

</xsl:stylesheet>