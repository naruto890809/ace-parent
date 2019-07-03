<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${bussPackage}.${module}.${logicModule}.domain.${clazzName}">
    <!-- Result Map-->
    <resultMap id="BaseResultMap" type="${bussPackage}.${module}.${logicModule}.domain.${clazzName}">
    <#list columnDatas as item>
        <result column="${item.columnName!}" property="${item.proColumnName}"/>
    </#list>
    </resultMap>

    <!-- ${tableName!} table all fields -->
    <sql id="Base_Column_List">
        ${SQL.columnFields!}
    </sql>

<#--定义指令# -->
<#macro mj>#</#macro>
<#macro md>$</#macro>
    <!-- 查询条件 -->
    <sql id="Example_Where_Clause">
        where 1=1
        <trim suffixOverrides=",">
            <if test="ids != null">
                and id in
                <foreach item="aId" index="index" collection="ids"
                         open="(" separator="," close=")">
                ${r'#{aId}'}
                </foreach>
            </if>

        <#list columnDatas as item>
            <#if item.dataType == 'String'>
            <if test="${item.proColumnName!} != null and ${item.proColumnName!} != ''">
            <#else>
            <if test="${item.proColumnName!} != null ">
            </#if>
            and ${item.columnName!} = <@mj />{ ${item.proColumnName!} }
            </if>
        </#list>
        </trim>
    </sql>


    <!-- 插入记录 -->
    <insert id="insert" parameterType="${bussPackage}.${module}.${logicModule}.domain.${clazzName}">
        ${SQL.insert!}
    </insert>


    <!-- 修改记录，只修改只不为空的字段 -->
    <update id="update" parameterType="${bussPackage}.${module}.${logicModule}.domain.${clazzName}">
    ${SQL.update!}
    </update>

    <!-- 删除记录 -->
    <delete id="deleteById" parameterType="String">
    ${SQL.delete!}
    </delete>

    <!-- 删除记录 -->
    <delete id="delete" parameterType="${bussPackage}.${module}.${logicModule}.domain.${clazzName}">
        delete from ${tableName!}
        <include refid="Example_Where_Clause"/>
    </delete>

    <!-- 根据id查询 ${codeName} -->
    <select id="getObject" resultMap="BaseResultMap" parameterType="String">
        ${SQL.selectById!}
    </select>


    <!-- ${codeName}列表总数-->
    <select id="getCount" resultType="java.lang.Integer">
        select count(1) from ${tableName!}
    </select>

    <!-- ${codeName}条件列表总数-->
    <select id="getCountBySelective" resultType="java.lang.Integer" parameterType="${bussPackage}.${module}.${logicModule}.domain.${clazzName}">
        select count(1) from ${tableName!}
        <include refid="Example_Where_Clause"/>
    </select>
    <!-- ${codeName}条件查询 ${codeName}-->
    <select id="getBySelective" resultMap="BaseResultMap"  parameterType="${bussPackage}.${module}.${logicModule}.domain.${clazzName}">
        select <include refid="Base_Column_List"/>
        from ${tableName!}
        <include refid="Example_Where_Clause"/>
    </select>

    <!-- 查询${codeName}列表 -->
    <select id="findList" resultMap="BaseResultMap" parameterType="${bussPackage}.${module}.${logicModule}.domain.${clazzName}">
        select
        <include refid="Base_Column_List"/>
        from ${tableName!}
        <include refid="Example_Where_Clause"/>
        order by  last_modify_time desc
    </select>

    <!-- 批量插入-->
    <insert id="saveBatch"  parameterType="java.util.List">
        insert into  ${tableName!}(<#list columnDatas as item>${item.columnName!}<#if item_index lt columnDatas?size-1>,</#if>
                                    </#list>)
        values
        <foreach collection="list" item="entity" index="index" separator="," >
            (<#list columnDatas as item><@mj />{entity.${item.proColumnName!}}<#if item_index lt columnDatas?size-1>,</#if>
             </#list>)
        </foreach>
    </insert>
    <!-- 批量删除-->
    <delete id="batchRemove" parameterType="java.util.HashMap">
        delete from ${tableName!}
        where id in
        <foreach collection="ids" item="item" index="index" open="(" close=")" separator=",">
        <@mj />{item}
        </foreach>
        <if test="corpCode != null and corpCode != ''">
            and corp_code =<@mj />{ corpCode }
        </if>
        <if test="parentCorpCode != null and parentCorpCode != ''">
            and parent_corp_code = <@mj />{ parentCorpCode }
        </if>
    </delete>
</mapper>
