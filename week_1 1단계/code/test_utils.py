#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
테스트 유틸리티 스크립트
테스트 환경 설정, 목업 서버, 헬퍼 함수들을 제공합니다.
"""

import json
import time
import threading
from http.server import HTTPServer, BaseHTTPRequestHandler
from urllib.parse import urlparse, parse_qs
import argparse

class MockAPIHandler(BaseHTTPRequestHandler):
    """Mock API 서버 핸들러"""
    
    def _set_headers(self, status_code=200, content_type='application/json'):
        """응답 헤더 설정"""
        self.send_response(status_code)
        self.send_header('Content-Type', content_type)
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
        self.send_header('Access-Control-Allow-Headers', 'Content-Type, Authorization')
        self.end_headers()
    
    def do_OPTIONS(self):
        """CORS preflight 요청 처리"""
        self._set_headers()
    
    def do_GET(self):
        """GET 요청 처리"""
        path = urlparse(self.path).path
        
        if path == '/api/v1/trends':
            self._handle_trends()
        elif path == '/api/v1/skills/popular':
            self._handle_popular_skills()
        elif path == '/api/v1/jobs/categories':
            self._handle_job_categories()
        elif path == '/api/v1/github/profile':
            self._handle_github_profile()
        else:
            self._handle_404()
    
    def do_POST(self):
        """POST 요청 처리"""
        path = urlparse(self.path).path
        
        # 요청 본문 읽기
        content_length = int(self.headers.get('Content-Length', 0))
        post_data = self.rfile.read(content_length)
        
        try:
            request_data = json.loads(post_data.decode('utf-8'))
        except:
            request_data = {}
        
        if path == '/api/v1/auth/signup':
            self._handle_signup(request_data)
        elif path == '/api/v1/auth/login':
            self._handle_login(request_data)
        else:
            self._handle_404()
    
    def _handle_trends(self):
        """트렌드 데이터 반환"""
        trends_data = [
            {"id": 1, "name": "Python", "popularity": 95, "category": "programming"},
            {"id": 2, "name": "JavaScript", "popularity": 90, "category": "programming"},
            {"id": 3, "name": "React", "popularity": 85, "category": "framework"},
            {"id": 4, "name": "Docker", "popularity": 80, "category": "devops"},
            {"id": 5, "name": "Kubernetes", "popularity": 75, "category": "devops"}
        ]
        
        self._set_headers(200)
        self.wfile.write(json.dumps(trends_data, ensure_ascii=False).encode('utf-8'))
    
    def _handle_popular_skills(self):
        """인기 스킬 데이터 반환"""
        skills_data = [
            {"skill_name": "Python", "demand_score": 95, "growth_rate": 15.5},
            {"skill_name": "JavaScript", "demand_score": 90, "growth_rate": 12.3},
            {"skill_name": "Java", "demand_score": 85, "growth_rate": 8.7},
            {"skill_name": "Go", "demand_score": 80, "growth_rate": 25.1},
            {"skill_name": "Rust", "demand_score": 75, "growth_rate": 35.2}
        ]
        
        self._set_headers(200)
        self.wfile.write(json.dumps(skills_data, ensure_ascii=False).encode('utf-8'))
    
    def _handle_job_categories(self):
        """직군 카테고리 데이터 반환"""
        categories_data = [
            {"category_id": 1, "category_name": "백엔드 개발자", "job_count": 1250},
            {"category_id": 2, "category_name": "프론트엔드 개발자", "job_count": 980},
            {"category_id": 3, "category_name": "풀스택 개발자", "job_count": 750},
            {"category_id": 4, "category_name": "DevOps 엔지니어", "job_count": 420},
            {"category_id": 5, "category_name": "데이터 사이언티스트", "job_count": 380}
        ]
        
        self._set_headers(200)
        self.wfile.write(json.dumps(categories_data, ensure_ascii=False).encode('utf-8'))
    
    def _handle_github_profile(self):
        """GitHub 프로필 데이터 반환 (인증 확인)"""
        auth_header = self.headers.get('Authorization')
        
        if not auth_header or not auth_header.startswith('Bearer '):
            self._set_headers(401)
            error_response = {"error": "인증 토큰이 필요합니다"}
            self.wfile.write(json.dumps(error_response, ensure_ascii=False).encode('utf-8'))
            return
        
        token = auth_header.split(' ')[1]
        
        # 간단한 토큰 검증 (실제로는 JWT 검증 등을 수행)
        if token == 'valid_test_token':
            profile_data = {
                "username": "testuser",
                "name": "테스트 사용자",
                "email": "test@example.com",
                "public_repos": 15,
                "followers": 42,
                "following": 18,
                "bio": "Full-stack developer passionate about open source"
            }
            self._set_headers(200)
            self.wfile.write(json.dumps(profile_data, ensure_ascii=False).encode('utf-8'))
        else:
            self._set_headers(401)
            error_response = {"error": "유효하지 않은 토큰입니다"}
            self.wfile.write(json.dumps(error_response, ensure_ascii=False).encode('utf-8'))
    
    def _handle_signup(self, request_data):
        """회원가입 처리"""
        email = request_data.get('email', '')
        password = request_data.get('password', '')
        name = request_data.get('name', '')
        
        # 유효성 검사
        if not email or '@' not in email:
            self._set_headers(400)
            error_response = {"error": "유효한 이메일을 입력해주세요"}
            self.wfile.write(json.dumps(error_response, ensure_ascii=False).encode('utf-8'))
            return
        
        if len(password) < 8:
            self._set_headers(400)
            error_response = {"error": "비밀번호는 8자 이상이어야 합니다"}
            self.wfile.write(json.dumps(error_response, ensure_ascii=False).encode('utf-8'))
            return
        
        # 중복 이메일 체크 (시뮬레이션)
        if email == 'existing@example.com':
            self._set_headers(409)
            error_response = {"error": "이미 존재하는 이메일입니다"}
            self.wfile.write(json.dumps(error_response, ensure_ascii=False).encode('utf-8'))
            return
        
        # 성공 응답
        self._set_headers(201)
        success_response = {
            "message": "회원가입이 완료되었습니다",
            "user_id": 12345,
            "email": email
        }
        self.wfile.write(json.dumps(success_response, ensure_ascii=False).encode('utf-8'))
    
    def _handle_login(self, request_data):
        """로그인 처리"""
        email = request_data.get('email', '')
        password = request_data.get('password', '')
        
        # 로그인 검증 (시뮬레이션)
        if email == 'test@example.com' and password == 'password123':
            self._set_headers(200)
            success_response = {
                "message": "로그인 성공",
                "token": "valid_test_token",
                "user": {
                    "id": 12345,
                    "email": email,
                    "name": "테스트 사용자"
                }
            }
            self.wfile.write(json.dumps(success_response, ensure_ascii=False).encode('utf-8'))
        else:
            self._set_headers(401)
            error_response = {"error": "이메일 또는 비밀번호가 잘못되었습니다"}
            self.wfile.write(json.dumps(error_response, ensure_ascii=False).encode('utf-8'))
    
    def _handle_404(self):
        """404 에러 처리"""
        self._set_headers(404)
        error_response = {"error": "요청한 리소스를 찾을 수 없습니다"}
        self.wfile.write(json.dumps(error_response, ensure_ascii=False).encode('utf-8'))
    
    def log_message(self, format, *args):
        """로그 메시지 포맷팅"""
        print(f"[{time.strftime('%Y-%m-%d %H:%M:%S')}] {format % args}")

class MockServer:
    """Mock API 서버"""
    
    def __init__(self, host='localhost', port=3000):
        self.host = host
        self.port = port
        self.server = None
        self.thread = None
    
    def start(self):
        """서버 시작"""
        try:
            self.server = HTTPServer((self.host, self.port), MockAPIHandler)
            self.thread = threading.Thread(target=self.server.serve_forever)
            self.thread.daemon = True
            self.thread.start()
            print(f"🚀 Mock API 서버 시작: http://{self.host}:{self.port}")
            print("📋 사용 가능한 엔드포인트:")
            print("  GET  /api/v1/trends")
            print("  GET  /api/v1/skills/popular")
            print("  GET  /api/v1/jobs/categories")
            print("  POST /api/v1/auth/signup")
            print("  POST /api/v1/auth/login")
            print("  GET  /api/v1/github/profile (인증 필요)")
            return True
        except Exception as e:
            print(f"❌ 서버 시작 실패: {e}")
            return False
    
    def stop(self):
        """서버 중지"""
        if self.server:
            self.server.shutdown()
            print("🛑 Mock API 서버 중지됨")
    
    def is_running(self):
        """서버 실행 상태 확인"""
        return self.thread and self.thread.is_alive()

def run_mock_server(host='localhost', port=3000):
    """Mock 서버 실행"""
    server = MockServer(host, port)
    
    if server.start():
        try:
            print("\n🔄 서버가 실행 중입니다. Ctrl+C로 중지하세요.")
            while server.is_running():
                time.sleep(1)
        except KeyboardInterrupt:
            print("\n\n🛑 서버 중지 요청 받음...")
            server.stop()
    else:
        print("❌ 서버 시작에 실패했습니다.")

def create_test_data():
    """테스트용 샘플 데이터 생성"""
    test_accounts = {
        "valid_account": {
            "email": "test@example.com",
            "password": "password123",
            "name": "테스트 사용자"
        },
        "existing_account": {
            "email": "existing@example.com",
            "password": "password123",
            "name": "기존 사용자"
        },
        "invalid_email": {
            "email": "invalid-email",
            "password": "password123",
            "name": "잘못된 이메일"
        },
        "weak_password": {
            "email": "weak@example.com",
            "password": "123",
            "name": "약한 비밀번호"
        }
    }
    
    # 테스트 데이터 파일 저장
    with open('/workspace/code/test_data.json', 'w', encoding='utf-8') as f:
        json.dump(test_accounts, f, ensure_ascii=False, indent=2)
    
    print("✅ 테스트 데이터 생성 완료: /workspace/code/test_data.json")

def main():
    """메인 실행 함수"""
    parser = argparse.ArgumentParser(description='테스트 유틸리티')
    parser.add_argument('command', choices=['server', 'data'], 
                       help='실행할 명령: server (Mock 서버 실행), data (테스트 데이터 생성)')
    parser.add_argument('--host', default='localhost', help='서버 호스트 (기본값: localhost)')
    parser.add_argument('--port', type=int, default=3000, help='서버 포트 (기본값: 3000)')
    
    args = parser.parse_args()
    
    if args.command == 'server':
        run_mock_server(args.host, args.port)
    elif args.command == 'data':
        create_test_data()

if __name__ == "__main__":
    main()
