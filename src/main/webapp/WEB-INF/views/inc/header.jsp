<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MySchool</title>
    <style>
        * {
            box-sizing: border-box;
        }

        .top{
            border-bottom: 1px solid #d5d5d5;
            margin-bottom: 35px;
            background-color: #eeeeee;
            display: flex;
        }

        .top h1{
            padding: 5px 25px;
            font-size: 24px;
            color: #06f;
            flex: 1;
        }

        .top ul{
            flex: none;
            margin-left: 0;
            padding: 0;
            margin: 0;
            list-style: none;
            margin-right: 20px;
            display: flex;
        }

        .top ul li{
            margin: auto 5px;
            box-sizing: content-box !important;
            padding-bottom: 5px;
            padding-top: 10px;
            border-bottom: 5px solid #0000;
        }

        .top ul li a{
            color: #000;
            text-decoration: none;
        }

        .top ul li:hover{
            border-bottom: 5px solid #06f;
        }

        .top ul li:hover a{
            color: #06f;
        }

        .bottom{
            margin-top: 35px;
            border-top: 1px solid #eee;
            padding: 15px 0;
            display: flex;
        }

        .bottome h1{
            color: #777;
            font-size: 18px;
            font-weight: 600;
            padding: 0 20px;
            flex: 1;
        }

        .bottom address{
            display: block;
            flex: none;
            margin: auto 0;
            margin-left: auto;
            color: #777;
            font-size: 14px;
            font-weight: 300;
            padding: 0 20px
        }
    </style>
</head>
</html>