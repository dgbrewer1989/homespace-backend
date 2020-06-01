create table profile (
	id uuid default uuid_generate_v4() primary key,
	user_id uuid not null references users(id),
	unique (user_id, id)
);