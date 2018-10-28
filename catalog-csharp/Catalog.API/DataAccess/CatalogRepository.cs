using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Catalog.API.Infrastructure;
using Catalog.API.Models;
using Microsoft.EntityFrameworkCore;

namespace Catalog.API.DataAccess
{
    public class CatalogRepository<TEntity> : ICatalogRepository<TEntity> where TEntity : class, ICatalogModel
    {
        private readonly CatalogContext context;
        private readonly DbSet<TEntity> dbSet;
        public CatalogRepository(CatalogContext context)
        {
            this.context = context;
            this.dbSet = context.Set<TEntity>();
        }
        public Task Delete(Guid id)
        {
            var entity = dbSet.Find(id);
            return Delete(entity);
        }
        public Task Delete(TEntity entity)
        {
            dbSet.Remove(entity);
            return Task.CompletedTask;
        }
        public Task<List<TEntity>> Get()
        {
            return dbSet.ToListAsync();
        }
        public Task<TEntity> Get(Guid id)
        {
            return dbSet.FindAsync(id);
        }
        public Task<List<TEntity>> Get(Guid[] ids)
        {
            return dbSet.Where(e => ids.Contains(e.Id)).ToListAsync();
        }
        public Task<List<TEntity>> Get(Expression<Func<TEntity, bool>> filter, Func<IQueryable<TEntity>, IOrderedQueryable<TEntity>> orderBy = null)
        {
            IQueryable<TEntity> query = dbSet;

            if (filter != null)
            {
                query = query.Where(filter);
            }

            if (orderBy != null)
            {
                return orderBy(query).ToListAsync();
            }

            return query.ToListAsync();
        }
        public Task Insert(TEntity entity)
        {
            return dbSet.AddAsync(entity);
        }
        public Task Update(TEntity entity)
        {
            throw new NotImplementedException();
        }
    }
}